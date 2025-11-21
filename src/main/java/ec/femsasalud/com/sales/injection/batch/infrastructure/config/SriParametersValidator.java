package ec.femsasalud.com.sales.injection.batch.infrastructure.config;

import ec.femsasalud.com.sales.injection.batch.application.service.ParametrosService;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Validador que verifica la existencia de parámetros SRI obligatorios
 * al iniciar la aplicación.
 *
 * Falla rápidamente si los parámetros no están configurados en
 * FA_PARAMETROS_FACTURADOR, evitando que la aplicación arranque
 * con configuración incompleta.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SriParametersValidator implements ApplicationRunner {

    private final ParametrosService parametrosService;

    @Value("${sri.wsdl.autorizacion.url:}")
    private String defaultWsdlUrl;

    @Value("${sri.ambiente:}")
    private String defaultAmbiente;

    @Value("${sri.xml.storage.path:}")
    private String defaultXmlStoragePath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=================================================================");
        log.info("Validando parámetros SRI obligatorios en FA_PARAMETROS_FACTURADOR");
        log.info("=================================================================");

        // Obtener parámetros
        String wsdlUrl = parametrosService.getParametroOrDefault(
            ParametroKey.SRI_WSDL_AUTORIZACION_URL, defaultWsdlUrl);
        String ambiente = parametrosService.getParametroOrDefault(
            ParametroKey.SRI_AMBIENTE, defaultAmbiente);
        String xmlStoragePath = parametrosService.getParametroOrDefault(
            ParametroKey.SRI_XML_STORAGE_PATH, defaultXmlStoragePath);

        // Validar parámetros obligatorios
        boolean hasErrors = false;

        if (wsdlUrl == null || wsdlUrl.trim().isEmpty()) {
            log.error("❌ PARÁMETRO FALTANTE: sri_wsdl_autorizacion_url");
            log.error("   Debe estar configurado en FA_PARAMETROS_FACTURADOR");
            log.error("   Ejemplo: https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
            hasErrors = true;
        } else {
            log.info("✓ sri_wsdl_autorizacion_url: {}", wsdlUrl);
        }

        if (ambiente == null || ambiente.trim().isEmpty()) {
            log.error("❌ PARÁMETRO FALTANTE: sri_ambiente");
            log.error("   Debe estar configurado en FA_PARAMETROS_FACTURADOR");
            log.error("   Valores válidos: 1 (PRUEBAS), 2 (PRODUCCIÓN)");
            hasErrors = true;
        } else {
            log.info("✓ sri_ambiente: {}", ambiente);
        }

        if (xmlStoragePath == null || xmlStoragePath.trim().isEmpty()) {
            log.error("❌ PARÁMETRO FALTANTE: sri_xml_storage_path");
            log.error("   Debe estar configurado en FA_PARAMETROS_FACTURADOR");
            log.error("   Ejemplo: /factelectro/documentos2015/factelectro/documentos2015/factura/autorizado");
            hasErrors = true;
        } else {
            log.info("✓ sri_xml_storage_path: {}", xmlStoragePath);
        }

        if (hasErrors) {
            log.error("=================================================================");
            log.error("ERROR: Parámetros SRI no configurados correctamente");
            log.error("=================================================================");
            log.error("Por favor, configure los siguientes parámetros en la tabla:");
            log.error("FA_PARAMETROS_FACTURADOR");
            log.error("");
            log.error("Ejemplo SQL:");
            log.error("INSERT INTO FA_PARAMETROS_FACTURADOR (CLAVE, VALOR, DESCRIPCION)");
            log.error("VALUES ('sri_wsdl_autorizacion_url', 'https://celcer.sri.gob.ec/...', 'URL WSDL SRI');");
            log.error("");
            log.error("INSERT INTO FA_PARAMETROS_FACTURADOR (CLAVE, VALOR, DESCRIPCION)");
            log.error("VALUES ('sri_ambiente', '1', 'Ambiente SRI (1=Pruebas, 2=Prod)');");
            log.error("");
            log.error("INSERT INTO FA_PARAMETROS_FACTURADOR (CLAVE, VALOR, DESCRIPCION)");
            log.error("VALUES ('sri_xml_storage_path', '/factelectro/documentos2015/...', 'Path XMLs');");
            log.error("=================================================================");

            throw new IllegalStateException(
                "Parámetros SRI obligatorios no configurados en FA_PARAMETROS_FACTURADOR. " +
                "Revise los logs para más detalles.");
        }

        log.info("=================================================================");
        log.info("✓ Todos los parámetros SRI están configurados correctamente");
        log.info("=================================================================");
    }
}
