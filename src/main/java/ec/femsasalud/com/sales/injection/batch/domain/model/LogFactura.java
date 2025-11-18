package ec.femsasalud.com.sales.injection.batch.domain.model;

import jakarta.persistence.Lob;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record LogFactura(
        String code,
        String error,
        BigDecimal intentos,
        @Lob
        String json,
        String mensaje,
        String reintegrar,
        String usuarioInserta,
        String orderId,
        Date businessDate,
        String documentType
) {
}
