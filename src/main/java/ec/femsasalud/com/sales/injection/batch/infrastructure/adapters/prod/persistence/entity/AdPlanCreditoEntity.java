package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AD_PLANES_CREDITO")
@Data
public class AdPlanCreditoEntity {

    @Id
    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "TARJETA_CREDITO")
    private String tarjetaCredito;

    @Column(name = "CUOTAS_TARJETAHABIENTE")
    private Integer cuotasTarjetahabiente;


}
