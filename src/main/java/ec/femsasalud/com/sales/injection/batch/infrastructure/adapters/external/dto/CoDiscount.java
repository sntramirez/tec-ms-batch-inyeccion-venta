package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoDiscount {
    private BigDecimal amount;
    private Long quantity;
    private String discountType;
    private String promotionId;
    private String campaignId;
    private String level;
    private Boolean paymentDiscount;
    private String categoryName;
    private String generalName;
    private int usedPoints;
    private String promotionBI;
    private List<CoProductDiscountDetail> productDiscountDetail;
}
