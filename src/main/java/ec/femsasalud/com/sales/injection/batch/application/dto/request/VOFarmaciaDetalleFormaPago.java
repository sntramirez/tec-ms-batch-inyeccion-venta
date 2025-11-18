package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VOFarmaciaDetalleFormaPago {
	
	private Long documentoVenta;
	private Long farmacia;
	private String formaPago;
	private BigDecimal pvpFactura;
	private BigDecimal ventaFactura;
	private String numeroTarjeta;
	private String numeroOda;
	private String numeroAutorizacion;
	private String numeroAutorizacionBoletin;
	private String numeroAutorizacionFybeca;
	private BigDecimal interes;
	private Long numeroCuotas;
	private String tarjetaDescuento;
	private String tarjetaDt;
	private String tarjetaHabiente;
	private Date fechaCaducidad;
	private String usuario;
	private String telefono;
	private String medioDescuento;
	private Long creditoEmpleado;
	private Long empresa;
	private Long empleado;
	private Long cliente;
	private String planCredito;
	private Long chequeRecibido;
	private Long remision;
	private String tipoCredito;
	private String tarjetaVitalcard;
	private String tipoServicio;
	private BigDecimal costoFactura;
	private String autorizacionFvc;
	private String cpVar6;
	private String cpVar7;
	private String cpVar8;
	private Date cpDate1;
	private Date cpDate2;
	
	public void setDetalleFormaPagoValue(Long sequential, Long localId, BigDecimal cost, VOPayment payment, VOFactura facturaRequest) {
		this.documentoVenta=sequential;
		this.farmacia=localId;
		this.formaPago=payment.paymentMethod();
		//this.pvpFactura=facturaRequest.getTotal().subtract(facturaRequest.getDiscountTotal());
		this.pvpFactura=facturaRequest.getPayments().get(0).amount();
		this.numeroTarjeta= payment.numeroTarjeta();
		this.interes=payment.interest();
		if (payment.quota() != null && !payment.quota().isEmpty())
			this.numeroCuotas=Long.parseLong(payment.quota());
		else
			this.numeroCuotas=null;
		this.ventaFactura=facturaRequest.getPayments().get(0).amount();
		this.costoFactura=cost;
		this.numeroAutorizacion=payment.authorizationBulletin();
		this.numeroAutorizacionBoletin=payment.authorizationCode();
		//this.remision=new Long("1");
	}
	

}
