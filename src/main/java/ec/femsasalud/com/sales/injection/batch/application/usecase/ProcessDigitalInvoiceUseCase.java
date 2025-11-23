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

import java.math.BigDecimal;
import java.util.*;

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
        // Mantener CODIGO en 200 y MENSAJE sin cambios
        // Solo actualizar fecha y usuario
        colaFactura.setFechaActualiza(new Date());
        colaFactura.setUsuarioActualiza("SRI_BATCH");

        // Resetear error e intentos al procesar exitosamente
        // ERROR = '1' indica que no hay error (estado OK)
        colaFactura.setError("1");
        colaFactura.setIntentos(BigDecimal.ZERO);

        colaFacturaRepository.save(colaFactura);
    }

    private void markAsError(FaColaFacturaDigitalEntity colaFactura, String errorMessage) {
        // Incrementar contador de intentos
        BigDecimal intentosActuales = colaFactura.getIntentos() != null ? colaFactura.getIntentos() : BigDecimal.ZERO;
        BigDecimal nuevosIntentos = intentosActuales.add(BigDecimal.ONE);

        colaFactura.setIntentos(nuevosIntentos);

        // Si ya llegó a 3 intentos, marcar con error "S" (SRI)
        if (nuevosIntentos.compareTo(BigDecimal.valueOf(3)) >= 0) {
            colaFactura.setError("S");
            log.error("Factura con 3 intentos fallidos - Clave de acceso: {} - Marcada con error S - Error: {}",
                colaFactura.getClaveAcceso(), errorMessage);
        } else {
            log.warn("Factura no procesada (Intento {}/3) - Clave de acceso: {} - Error: {}",
                nuevosIntentos, colaFactura.getClaveAcceso(), errorMessage);
        }

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

    /**
     * Método de debug para consultar el estado de un documento por clave de acceso
     * Verifica en FA_COLA_FACTURA_DIGITAL y en las tablas de destino
     */
    public Map<String, Object> debugDocument(String claveAcceso) {
        Map<String, Object> debug = new HashMap<>();

        try {
            // Buscar en FA_COLA_FACTURA_DIGITAL
            List<FaColaFacturaDigitalEntity> cola = colaFacturaRepository.findPendingDigitalInvoices()
                .stream()
                .filter(f -> claveAcceso.equals(f.getClaveAcceso()))
                .toList();

            if (!cola.isEmpty()) {
                FaColaFacturaDigitalEntity item = cola.get(0);
                debug.put("enCola", true);
                debug.put("documentType", item.getDocumentType());
                debug.put("codigo", item.getCodigo());
                debug.put("error", item.getError());
                debug.put("intentos", item.getIntentos());
                debug.put("usuarioActualiza", item.getUsuarioActualiza());
                debug.put("mensaje", "Documento encontrado en FA_COLA_FACTURA_DIGITAL");
            } else {
                debug.put("enCola", false);
                debug.put("mensaje", "Documento NO encontrado en FA_COLA_FACTURA_DIGITAL o ya fue procesado");
            }

            debug.put("claveAcceso", claveAcceso);

        } catch (Exception e) {
            log.error("Error al consultar documento en debug", e);
            debug.put("error", e.getMessage());
        }

        return debug;
    }

    /**
     * Resetea los errores SRI y número de intentos para reprocesar las facturas
     * Este método se debe ejecutar cuando el SRI vuelva a estar disponible después de mantenimiento
     */
    @Transactional
    public int resetSriErrors() {
        log.info("Iniciando reseteo de errores SRI");

        try {
            // Obtener facturas con error SRI
            List<FaColaFacturaDigitalEntity> invoicesWithError = colaFacturaRepository.findInvoicesWithSriError();
            log.info("Se encontraron {} facturas con error SRI para resetear", invoicesWithError.size());

            // Resetear error e intentos
            // ERROR = '1' indica que no hay error (estado OK)
            for (FaColaFacturaDigitalEntity invoice : invoicesWithError) {
                invoice.setError("1");
                invoice.setIntentos(BigDecimal.ZERO);
                log.info("Reseteando error SRI para factura: {}", invoice.getClaveAcceso());
            }

            // Guardar todos los cambios
            colaFacturaRepository.saveAll(invoicesWithError);

            log.info("Reseteo de errores SRI completado. {} facturas serán reprocesadas", invoicesWithError.size());

            return invoicesWithError.size();

        } catch (Exception e) {
            log.error("Error al resetear errores SRI", e);
            throw new RuntimeException("Error al resetear errores SRI", e);
        }
    }
}
