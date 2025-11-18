package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VOFarmaciaNotaCredito {
	
	private Long codigo;

	private Timestamp fechaCancelacion;

	private Date fecha;

	private String cancelada;

	private String usuario;

	private BigDecimal valor;

	private Long documentoVenta;

	private String formaPago;

	private Long farmacia;

	private Long empleadoCobra;

	private String tipoCancelacion;

	private Long farmaciaCanje;

	private Long documentoCancelacion;


}
