package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VODiscount;
import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOProduct;

import java.math.BigDecimal;

public interface CalculadoraRepository {

    BigDecimal getDiscountAmount(VOProduct voProduct);

    Long getDiscountQuantity(VOProduct voProduct);

    String getECommerceCampaignId(VODiscount voDiscount);

    String getDiscountType(VODiscount voDiscount);

    String getPromotionBI(VODiscount voDiscount);

    BigDecimal getTaxAmount(VOProduct voProduct, BigDecimal priceListOri);

    BigDecimal getAffectedTax(VOProduct voProduct, BigDecimal priceListOri);

    BigDecimal getFinalPrice(VOProduct voProduct);

    BigDecimal getTotalPrice(VOProduct voProduct);

    BigDecimal getPriceList(long l, VOProduct voProduct);
}
