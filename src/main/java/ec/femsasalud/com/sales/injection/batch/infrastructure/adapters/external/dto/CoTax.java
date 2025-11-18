package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoTax {
    private BigDecimal taxAmount;
    private String taxId;
    private BigDecimal affectedTax;
}
