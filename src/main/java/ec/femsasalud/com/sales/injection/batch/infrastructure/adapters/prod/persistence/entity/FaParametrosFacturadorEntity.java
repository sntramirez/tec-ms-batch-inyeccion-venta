package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "FA_PARAMETROS_FACTURADOR", schema = "FARMACIAS")
public class FaParametrosFacturadorEntity {

    @Id
    private BigDecimal id;
    private String clave;
    private String valor;

}
