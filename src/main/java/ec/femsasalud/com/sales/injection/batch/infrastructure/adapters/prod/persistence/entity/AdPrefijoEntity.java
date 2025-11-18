package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AD_PREFIJOS")
@Data
public class AdPrefijoEntity {

    @Id
    @Column(name = "PREFIJOS")
    private String prefijos;

    @Column(name = "TARJETA")
    private String tarjeta;

    @Column(name = "ACTIVO")
    private String activo;

}
