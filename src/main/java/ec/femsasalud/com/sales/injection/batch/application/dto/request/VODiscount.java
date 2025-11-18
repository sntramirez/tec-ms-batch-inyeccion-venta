package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VODiscount {
    private String campaignId;
    private String promotionId;
    private Integer usedPoints;
    private Double benefit;
    private Double amount;
    private String convertion;
    private Boolean loyaltyDiscount;
    private BigDecimal discountAmount;
    private Long discountQuantity;
    private String eCommerceId;
    private String eCommerceCampaignId;
    private String discountType;
    private String discountApplicationType;
    private List<V0ProductDiscountDetail> productDiscountDetail;
}


