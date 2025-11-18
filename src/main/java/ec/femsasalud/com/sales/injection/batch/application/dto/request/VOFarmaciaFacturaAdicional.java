package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VOFarmaciaFacturaAdicional {

	private Long  farmacia;			
	private Long  documentoVenta;
	private String tipoImpresion;
	private Long  cp_num1;
	private Long  cp_num2;
	private String cpVar3;
	private String cpVar4;
	private String cpVar5;
	private String cpVar6;
	private String cpVar7;
	private Date cpDate1;
	private Date cpDate2;
	
}
