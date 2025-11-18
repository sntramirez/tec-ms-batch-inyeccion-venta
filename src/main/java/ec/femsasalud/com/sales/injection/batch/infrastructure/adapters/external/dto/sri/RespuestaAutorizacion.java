package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "RespuestaAutorizacionComprobante")
public class RespuestaAutorizacion {

    @JacksonXmlProperty(localName = "claveAccesoConsultada")
    private String claveAccesoConsultada;

    @JacksonXmlProperty(localName = "numeroComprobantes")
    private String numeroComprobantes;

    @JacksonXmlProperty(localName = "autorizaciones")
    private Autorizaciones autorizaciones;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Autorizaciones {
        @JacksonXmlProperty(localName = "autorizacion")
        private List<Autorizacion> autorizacion;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Autorizacion {
        @JacksonXmlProperty(localName = "estado")
        private String estado;

        @JacksonXmlProperty(localName = "numeroAutorizacion")
        private String numeroAutorizacion;

        @JacksonXmlProperty(localName = "fechaAutorizacion")
        private String fechaAutorizacion;

        @JacksonXmlProperty(localName = "ambiente")
        private String ambiente;

        @JacksonXmlProperty(localName = "comprobante")
        private String comprobante;

        @JacksonXmlProperty(localName = "mensajes")
        private Mensajes mensajes;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Mensajes {
        @JacksonXmlProperty(localName = "mensaje")
        private List<Mensaje> mensaje;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Mensaje {
        @JacksonXmlProperty(localName = "identificador")
        private String identificador;

        @JacksonXmlProperty(localName = "mensaje")
        private String mensaje;

        @JacksonXmlProperty(localName = "informacionAdicional")
        private String informacionAdicional;

        @JacksonXmlProperty(localName = "tipo")
        private String tipo;
    }
}
