package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AD_TARJETAS_CREDITO")
public class AdTarjetaCreditoEntity {

    @Id
    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "EMISOR")
    private Integer emisor;

}
