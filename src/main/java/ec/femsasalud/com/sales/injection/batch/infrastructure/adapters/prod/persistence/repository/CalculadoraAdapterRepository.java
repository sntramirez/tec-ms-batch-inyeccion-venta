package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VODiscount;
import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOProduct;
import ec.femsasalud.com.sales.injection.batch.domain.repository.CalculadoraRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraAdapterRepository implements CalculadoraRepository {
    @Override
    public BigDecimal getDiscountAmount(VOProduct voProduct) {
        return BigDecimal.ZERO;
    }

    @Override
    public Long getDiscountQuantity(VOProduct voProduct) {
        return 0L;
    }

    @Override
    public String getECommerceCampaignId(VODiscount voDiscount) {
        return "";
    }

    @Override
    public String getDiscountType(VODiscount voDiscount) {
        return "";
    }

    @Override
    public String getPromotionBI(VODiscount voDiscount) {
        return "";
    }

    @Override
    public BigDecimal getTaxAmount(VOProduct voProduct, BigDecimal priceListOri) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getAffectedTax(VOProduct voProduct, BigDecimal priceListOri) {
        return null;
    }

    @Override
    public BigDecimal getFinalPrice(VOProduct voProduct) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalPrice(VOProduct voProduct) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getPriceList(long l, VOProduct voProduct) {
        return BigDecimal.ZERO;
    }
}
