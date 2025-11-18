package ec.femsasalud.com.sales.injection.batch.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FaColaFacturaDigital {

    private BigDecimal id;

    private String codigo;

    private String error;

    private Date fechaInserta;

    private BigDecimal intentos;

    private String json;

    private String mensaje;

    private String orderId;

    private String reintegrar;

    private String usuarioInserta;

    private Date fechaActualiza;

    private String usuarioActualiza;

    private Date businessDate;

    private String documentType;

    private String claveAcceso;

}
