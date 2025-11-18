package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AD_PLANES_CREDITO_ADI", schema = "ADMINISTRACION")
@Data
public class AdPlanCreditoAdicionalEntity {

    @Id
    @Column(name = "GROUPCODE")
    private String groupCode;

    @Column(name = "PLAN_CREDITO")
    private String planCredito;

}
