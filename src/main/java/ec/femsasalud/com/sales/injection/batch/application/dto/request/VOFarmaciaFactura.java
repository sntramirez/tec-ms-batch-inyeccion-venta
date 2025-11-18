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
@AllArgsConstructor
@NoArgsConstructor
public class VOFarmaciaFactura {
	
	private Long farmacia;
	private Long documentoVenta;
	private Timestamp fecha;
	private String numeroSRI;
	private BigDecimal costoTotalFactura;
	private BigDecimal pvpTotalFactura;
	private BigDecimal ventaTotalFactura;
	private String canalVenta;
	private BigDecimal valorIva;
	private String cancelada;
	private String tipoDocumento;
	private Long caja;
	private String tipoMovimiento;
	private String clasificacionMovimiento;
	private Long cliente;
	private Long documentoVentaPadre;
	private Long farmaciaPadre;
	private String usuario;
	private Long empleadoRealiza;
	private Long empleadoCobra;
	private Long persona;
	private String primerApellido;
	private String segundoApellido;
	private String nombres;
	private String identificacion;
	private String direccion;
	private Date fechaSistema;
	private String donacion;
	private String tratamientoContinuo;
	private Long empleadoEntrega;
	private String incluyeIva;
	private Long tomaPedidoDomicilio;
	private Integer direccionDomicilio;
	private String generaNotaCredito;
	private String calluser;

}
