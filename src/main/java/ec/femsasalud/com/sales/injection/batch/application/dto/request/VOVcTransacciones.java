package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class VOVcTransacciones {
	
	private Long codigo;
	private String numeroTarjeta;
	private String tipo_Movimiento;
	private String clasificacion_Movimiento;
	private Long farmacia;
	private String forma_Pago;
	private Long documento;
	private String numero_Factura;
	private String numero_Plazo;

	private BigDecimal saldo_Interes;
	private Timestamp fechaTransaccion;
	private Date fecha_Factura;
	private String llevado;
	private String protestado;
	private String pendiente;
	private String domicilio;
	private String usuario;
	private String nota_Ajuste;
	private String tipo_Credito;
	private String centro_Costos_VitalCard;
	private String cuota_Actual;
	private Long campo_Num1;
	private Long campo_Num2;
	private Date campo_Fecha1;
	private Date campo_Fecha2;
	private String campo_Varc1;
	private String campo_Varc2;
	private BigDecimal valor;
	private BigDecimal valorCuota;
	private BigDecimal valorInteres;
	private BigDecimal saldo;

	public void setVcTransaccionesValue(Long sequential, Long localId,
			String creditNumber, VOFactura facturaRequest, VOPayment pago) {
		this.clasificacion_Movimiento = "01";
		this.numeroTarjeta = creditNumber;
		//this.campo_Varc1 = numeroTarjeta.substring(0, 7);
		this.documento = sequential;
		this.farmacia = localId;
		this.valor = pago.amount();
		this.valorCuota = pago.amount();
		this.valorInteres = pago.interest();
		this.numero_Plazo = "1";
		this.saldo = pago.amount().subtract(pago.amountQuota());
		this.tipo_Credito="2";
		this.numero_Factura = facturaRequest.getAuthorizationCode().substring(24, 27) + "-"
				+ facturaRequest.getAuthorizationCode().substring(27, 30) + "-"
				+ facturaRequest.getAuthorizationCode().substring(30, 39);
		this.usuario = facturaRequest.getUser();
	}
	
}
