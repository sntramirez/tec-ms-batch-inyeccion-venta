package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CoBonusResponse(
        Voucher voucher,
        int code,
        String description
) {

    public record Voucher(
            String barcode,
            BigDecimal amount,
            String expirationDate
    ) {

    }
}
