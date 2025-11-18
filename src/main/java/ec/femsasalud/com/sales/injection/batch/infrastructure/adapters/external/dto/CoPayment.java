package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoPayment {
    private String paymentMethod;
    private BigDecimal amount;
    private BigDecimal affectedAmount;
    private String currency;
    private CoDetails details;
}
