package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VOSecuenciaElectronica {
	
	private Long farmacia;
	private Long secuencia;
	private Long documentoVenta;
	private String clasificacionDocumento;
	private String tipoMovimiento;
	private Long tipodocumento;
	private String cpVar1;
	private String cpVar2;

}
