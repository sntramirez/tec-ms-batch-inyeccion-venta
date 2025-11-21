package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ec.femsasalud.com.sales.injection.batch.domain.service.SriAuthorizationPort;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri.RespuestaAutorizacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class SriAutorizacionClient implements SriAuthorizationPort {

    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    public SriAutorizacionClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
        this.xmlMapper = new XmlMapper();
    }

    public RespuestaAutorizacion consultarAutorizacion(String wsdlUrl, String claveAcceso) {
        log.info("Consultando autorización para clave de acceso: {}", claveAcceso);

        String soapRequest = buildSoapRequest(claveAcceso);

        try {
            String response = webClient.post()
                    .uri(wsdlUrl)
                    .header("Content-Type", "text/xml; charset=utf-8")
                    .header("SOAPAction", "")
                    .bodyValue(soapRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            log.debug("Respuesta del SRI recibida");

            // Extraer el XML de la respuesta SOAP
            String xmlResponse = extractXmlFromSoapResponse(response);

            // Parsear la respuesta XML
            RespuestaAutorizacion respuesta = xmlMapper.readValue(xmlResponse, RespuestaAutorizacion.class);

            log.info("Autorización consultada exitosamente. Estado: {}",
                    respuesta.getAutorizaciones() != null &&
                            !respuesta.getAutorizaciones().getAutorizacion().isEmpty() ?
                            respuesta.getAutorizaciones().getAutorizacion().get(0).getEstado() : "SIN ESTADO");

            return respuesta;

        } catch (Exception e) {
            log.error("Error al consultar autorización para clave de acceso: {}", claveAcceso, e);
            throw new RuntimeException("Error al consultar autorización en el SRI", e);
        }
    }

    private String buildSoapRequest(String claveAcceso) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ec:autorizacionComprobante>\n" +
                "         <claveAccesoComprobante>" + claveAcceso + "</claveAccesoComprobante>\n" +
                "      </ec:autorizacionComprobante>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    private String extractXmlFromSoapResponse(String soapResponse) {
        // Extraer el contenido entre las etiquetas RespuestaAutorizacionComprobante
        int startIndex = soapResponse.indexOf("<RespuestaAutorizacionComprobante");
        if (startIndex == -1) {
            // Intentar con namespace
            startIndex = soapResponse.indexOf(":RespuestaAutorizacionComprobante");
            if (startIndex != -1) {
                startIndex = soapResponse.lastIndexOf("<", startIndex);
            }
        }

        int endIndex = soapResponse.indexOf("</RespuestaAutorizacionComprobante>");
        if (endIndex == -1) {
            endIndex = soapResponse.indexOf(":RespuestaAutorizacionComprobante>");
            if (endIndex != -1) {
                endIndex = soapResponse.indexOf(">", endIndex) + 1;
            }
        } else {
            endIndex += "</RespuestaAutorizacionComprobante>".length();
        }

        if (startIndex != -1 && endIndex != -1) {
            String extracted = soapResponse.substring(startIndex, endIndex);
            // Limpiar namespaces si existen
            extracted = extracted.replaceAll("<[a-zA-Z0-9]+:", "<").replaceAll("</[a-zA-Z0-9]+:", "</");
            return extracted;
        }

        log.warn("No se pudo extraer el XML de la respuesta SOAP");
        return soapResponse;
    }

    public String obtenerXmlAutorizado(RespuestaAutorizacion respuesta) {
        if (respuesta != null &&
                respuesta.getAutorizaciones() != null &&
                !respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {

            RespuestaAutorizacion.Autorizacion autorizacion = respuesta.getAutorizaciones().getAutorizacion().get(0);

            if ("AUTORIZADO".equalsIgnoreCase(autorizacion.getEstado())) {
                return autorizacion.getComprobante();
            }
        }
        return null;
    }
}
