package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VOCreditoDevolucion {
	
	private Long id;
	private Long  referencia;	
	private BigDecimal totalVenta;
	private BigDecimal totalPvp;
	private Date fecha;
	private String cancelada;
	private Long  cliente;
	private String formaPago;
	private Long  farmacia;
	private Long  documentoVenta;
	private String clasificacionMovimiento;
	private String tipoMovimiento;
	private Long  empleado;
	private Long  empresa;

}
