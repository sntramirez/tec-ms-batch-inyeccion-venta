package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VODetallesFormasPago_Adi {
	
	private Long documentoVenta;
	private Long farmacia;
	private String formaPago;
	
	private Long cpNum1;
	private Long cpNum2;
	private Long cpNum3;
	private Long cpNum4;
	private Long cpNum5;
	
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

	public void setDetalleFormaPagoAdValue(Long sequential,Long localId, String codigoMetodoPagoFybeca) {
		this.documentoVenta=sequential;
		this.farmacia=localId;
		this.formaPago=codigoMetodoPagoFybeca;
		this.cpNum1= Long.parseLong("0");
		if("PP".equals(codigoMetodoPagoFybeca))
			this.cpChar1="P";
	}
	
}
