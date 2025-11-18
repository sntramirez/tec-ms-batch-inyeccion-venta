package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoProduct {
    private String sku;
    private String description;
    private BigDecimal finalPrice;
    private BigDecimal priceList;
    private Long quantity;
    private String seller;
    private String sellerDocumentType;
    private String sellerDocument;
    private List<CoDiscount> discounts;
    private List<CoTax> taxes;
}
