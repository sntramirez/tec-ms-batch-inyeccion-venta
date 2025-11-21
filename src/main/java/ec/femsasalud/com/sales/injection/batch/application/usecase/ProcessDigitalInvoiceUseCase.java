package ec.femsasalud.com.sales.injection.batch.application.usecase;

import ec.femsasalud.com.sales.injection.batch.application.service.ParametrosService;
import ec.femsasalud.com.sales.injection.batch.domain.repository.ColaFacturaDigitalRepository;
import ec.femsasalud.com.sales.injection.batch.domain.service.DigitalInvoiceProcessorPort;
import ec.femsasalud.com.sales.injection.batch.domain.service.FileStoragePort;
import ec.femsasalud.com.sales.injection.batch.domain.service.SriAuthorizationPort;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri.RespuestaAutorizacion;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Caso de uso para procesar facturas digitales desde el SRI
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDigitalInvoiceUseCase {

    // Puertos - Domain Services
    private final SriAuthorizationPort sriAuthorizationPort;
    private final FileStoragePort fileStoragePort;
    private final DigitalInvoiceProcessorPort invoiceProcessorPort;

    // Puertos - Domain Repositories
    private final ColaFacturaDigitalRepository colaFacturaRepository;
    private final ParametrosService parametrosService;

    @Value("${sri.wsdl.autorizacion.url:}")
    private String defaultWsdlUrl;

    @Value("${sri.ambiente:}")
    private String defaultAmbiente;

    private static final String CODIGO_PROCESADO = "201";
    private static final String CODIGO_ERROR = "500";

    /**
     * Procesa todas las facturas digitales pendientes
     */
    @Transactional
    public void processPendingInvoices() {
        log.info("Iniciando procesamiento de facturas digitales pendientes");

        // Obtener parámetros del SRI
        String wsdlUrl = parametrosService.getParametroOrDefault(ParametroKey.SRI_WSDL_AUTORIZACION_URL, defaultWsdlUrl);
        String ambiente = parametrosService.getParametroOrDefault(ParametroKey.SRI_AMBIENTE, defaultAmbiente);

        // Validar que los parámetros obligatorios estén configurados
        validateRequiredParameter(wsdlUrl, "sri_wsdl_autorizacion_url",
            "La URL del WSDL del SRI debe estar configurada en FA_PARAMETROS_FACTURADOR");
        validateRequiredParameter(ambiente, "sri_ambiente",
            "El ambiente del SRI debe estar configurado en FA_PARAMETROS_FACTURADOR");

        log.info("Usando WSDL: {} - Ambiente: {}", wsdlUrl, ambiente);

        try {
            // Obtener facturas pendientes
            List<FaColaFacturaDigitalEntity> pendingInvoices = colaFacturaRepository.findPendingDigitalInvoices();
            log.info("Se encontraron {} facturas pendientes de procesar", pendingInvoices.size());

            int successful = 0;
            int failed = 0;

            for (FaColaFacturaDigitalEntity colaFactura : pendingInvoices) {
                try {
                    processInvoice(colaFactura, wsdlUrl);
                    successful++;
                } catch (Exception e) {
                    log.error("Error al procesar factura con clave de acceso: {}", colaFactura.getClaveAcceso(), e);
                    markAsError(colaFactura, e.getMessage());
                    failed++;
                }
            }

            log.info("Procesamiento completado. Exitosos: {}, Fallidos: {}", successful, failed);

        } catch (Exception e) {
            log.error("Error general en el procesamiento de facturas digitales", e);
            throw new RuntimeException("Error en el procesamiento de facturas digitales", e);
        }
    }

    /**
     * Procesa una factura individual
     */
    @Transactional
    public void processInvoice(FaColaFacturaDigitalEntity colaFactura, String wsdlUrl) {
        log.info("Procesando factura con clave de acceso: {}", colaFactura.getClaveAcceso());

        try {
            // 1. Consultar autorización en el SRI (Puerto)
            RespuestaAutorizacion respuesta = sriAuthorizationPort.consultarAutorizacion(wsdlUrl, colaFactura.getClaveAcceso());

            if (respuesta == null || respuesta.getAutorizaciones() == null ||
                    respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                throw new RuntimeException("No se obtuvo respuesta de autorización del SRI");
            }

            RespuestaAutorizacion.Autorizacion autorizacion = respuesta.getAutorizaciones().getAutorizacion().get(0);

            // 2. Verificar que esté autorizado
            if (!"AUTORIZADO".equalsIgnoreCase(autorizacion.getEstado())) {
                String mensaje = buildErrorMessage(autorizacion);
                throw new RuntimeException("Comprobante no autorizado. Estado: " + autorizacion.getEstado() + ". " + mensaje);
            }

            // 3. Obtener XML completo del SRI (no solo el comprobante)
            String xmlCompleto = respuesta.getXmlCompleto();
            if (xmlCompleto == null || xmlCompleto.isEmpty()) {
                throw new RuntimeException("No se pudo obtener el XML completo del SRI");
            }

            // 4. Guardar XML completo en filesystem (Puerto)
            String xmlFilePath = fileStoragePort.saveXmlFile(
                    xmlCompleto,
                    colaFactura.getClaveAcceso(),
                    colaFactura.getDocumentType()
            );

            // 5. Procesar y guardar en tb_factura o TB_NOTA_CREDITO (Puerto)
            invoiceProcessorPort.processAndSaveInvoice(colaFactura, autorizacion, xmlFilePath);

            // 6. Actualizar estado en FA_COLA_FACTURA_DIGITAL
            markAsProcessed(colaFactura, autorizacion.getNumeroAutorizacion());

            log.info("Factura procesada exitosamente: {}", colaFactura.getClaveAcceso());

        } catch (Exception e) {
            log.error("Error al procesar factura: {}", colaFactura.getClaveAcceso(), e);
            throw e;
        }
    }

    private void markAsProcessed(FaColaFacturaDigitalEntity colaFactura, String numeroAutorizacion) {
        colaFactura.setCodigo(CODIGO_PROCESADO);
        colaFactura.setMensaje("Procesado exitosamente. Autorización: " + numeroAutorizacion);
        colaFactura.setFechaActualiza(new Date());
        colaFactura.setUsuarioActualiza("SRI_BATCH");
        colaFacturaRepository.save(colaFactura);
    }

    private void markAsError(FaColaFacturaDigitalEntity colaFactura, String errorMessage) {
        colaFactura.setCodigo(CODIGO_ERROR);
        colaFactura.setError("ERROR");
        colaFactura.setMensaje(errorMessage != null && errorMessage.length() > 500 ?
                errorMessage.substring(0, 500) : errorMessage);
        colaFactura.setFechaActualiza(new Date());
        colaFactura.setUsuarioActualiza("SRI_BATCH");
        colaFacturaRepository.save(colaFactura);
    }

    private String buildErrorMessage(RespuestaAutorizacion.Autorizacion autorizacion) {
        if (autorizacion.getMensajes() == null || autorizacion.getMensajes().getMensaje() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (RespuestaAutorizacion.Mensaje mensaje : autorizacion.getMensajes().getMensaje()) {
            sb.append(mensaje.getMensaje()).append(". ");
        }
        return sb.toString();
    }

    private void validateRequiredParameter(String value, String parameterName, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            log.error("Parámetro requerido no configurado: {}", parameterName);
            throw new IllegalStateException(errorMessage + " (parámetro: " + parameterName + ")");
        }
    }
}
