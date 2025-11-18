package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DTOAuthorizedItem {
	
	BigDecimal cost;
	BigDecimal costWithoutTax;
	BigDecimal purchaseWithoutTax;
	BigDecimal saleUnit;
	BigDecimal stock;

}
