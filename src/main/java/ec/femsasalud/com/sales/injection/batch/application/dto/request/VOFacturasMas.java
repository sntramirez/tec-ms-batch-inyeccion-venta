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
public class VOFacturasMas {
	
	private Long documentoVenta;
	private Long farmacia;
	private String personaABF;
	private String autorizacionABF;
	private String contratoABF;

	private String cpVar1;
	private String cpVar2;
	private String cpVar3;
	private String cpVar4;

	private Long cp_num1;
	private Long cp_num2;
	private Long cp_num3;
	private Long cp_num4;

	private String cpChar1;
	private String cpChar2;
	private String cpChar3;
	private String cpChar4;

	private Date cpDate1;
	private Date cpDate2;
	private Date cpDate3;
	private Date cpDate4;

}
