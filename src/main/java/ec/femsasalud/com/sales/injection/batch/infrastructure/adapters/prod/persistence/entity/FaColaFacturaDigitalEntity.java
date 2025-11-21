package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="FA_COLA_FACTURA_DIGITAL",schema="FARMACIAS")
@org.hibernate.annotations.DynamicUpdate
public class FaColaFacturaDigitalEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "log_factura", sequenceName = "FARMACIAS.SEQ_FA_LOG_FACTURA_DIGITAL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_factura")
	private BigDecimal id;

	private String codigo;

	private String error;

	@Column(name="FECHA_INSERTA")
	private Date fechaInserta;

	private BigDecimal intentos;

	@Lob
	private String json;

	private String mensaje;

	@Column(name="ORDER_ID")
	private String orderId;

	private String reintegrar;

	@Column(name="USUARIO_INSERTA")
	private String usuarioInserta;
	
	@Column(name="FECHA_ACTUALIZA")
	private Date fechaActualiza;
	
	@Column(name="USUARIO_ACTUALIZA")
	private String usuarioActualiza;

    @Column(name="BUSINESS_DATE")
    private Date businessDate;

    @Column(name="DOCUMENT_TYPE")
    private String documentType;

    @Column(name="CLAVE_ACCESO")
    private String claveAcceso;


}
