package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "FA_LOG_FACTURA_DIGITAL", schema = "FARMACIAS")
public class FaLogFacturaDigitalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "log_factura", sequenceName = "FARMACIAS.SEQ_FA_LOG_FACTURA_DIGITAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_factura")
    private BigDecimal id;

    private String codigo;

    private String error;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_INSERTA")
    private Date fechaInserta;

    private BigDecimal intentos;

    @Lob
    private String json;

    private String mensaje;

    @Column(name = "ORDER_ID")
    private String orderId;

    private String reintegrar;

    @Column(name = "USUARIO_INSERTA")
    private String usuarioInserta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BUSINESS_DATE")
    private Date businessDate;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;
}
