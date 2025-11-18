package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoBonus {
    String docNumber;
    String docType;
    BigDecimal amount;
    String orderNumber;
}
