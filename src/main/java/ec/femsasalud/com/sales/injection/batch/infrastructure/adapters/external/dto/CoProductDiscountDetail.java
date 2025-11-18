package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoProductDiscountDetail {
    private String detail;
    private String discount;
    private String quantity;
    private String appliedEventValue;
    private String discountType;
    private String promotionId;
    private String promotionType;
    private String cicle;
}
