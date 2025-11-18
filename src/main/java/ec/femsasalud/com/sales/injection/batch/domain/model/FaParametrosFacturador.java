package ec.femsasalud.com.sales.injection.batch.domain.model;

import lombok.Data;

import java.math.BigDecimal;


public record FaParametrosFacturador(
        BigDecimal id,
        String clave,
        String valor
) {
}
