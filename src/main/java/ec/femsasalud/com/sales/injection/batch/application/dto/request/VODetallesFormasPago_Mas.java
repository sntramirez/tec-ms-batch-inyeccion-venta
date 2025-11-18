package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VODetallesFormasPago_Mas {
	
	private Long documentoVenta;
	private Long farmacia;
	private String formaPago;
	private String emisorAutoCh;
	private BigDecimal cpNum1;
	private BigDecimal cpNum2;
	private BigDecimal cpNum3;
	private BigDecimal cpNum4;
	private BigDecimal cpNum5;

	private String cpVar1;
	private String cpVar2;
	private String cpVar3;
	private String cpVar4;
	private String cpVar5;

	private String cpChar1;
	private String cpChar2;
	private String cpChar3;
	private String cpChar4;
	private String cpChar5;

	private Date cpDate1;
	private Date cpDate2;
	private Date cpDate3;
	private Date cpDate4;
	
	public void setDetalleFormaPagoMasValue(Long sequential, Long localId, String codigoMetodoPagoFybeca,
			VOFactura facturaRequest) {
		this.documentoVenta = sequential;
		this.farmacia = localId;
		this.formaPago = codigoMetodoPagoFybeca;
		if("PP".equals(codigoMetodoPagoFybeca)) {
			this.cpNum2 = facturaRequest.getBase0();
			this.cpNum3 = facturaRequest.getBase12();
			this.cpNum4 = BigDecimal.valueOf(0.00);
			this.cpNum5 = BigDecimal.valueOf(facturaRequest.getTaxTotal());
			this.cpVar4 = "/";
		}

	}

}
