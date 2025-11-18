package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VOResponseConfirmacionPago {

	private String status;
	private String description;

	public VOResponseConfirmacionPago setResponse(VOResponseConfirmacionPago response, String status, String description) {
		setStatus(status);
		setDescription(description);
		return response;
	}

}
