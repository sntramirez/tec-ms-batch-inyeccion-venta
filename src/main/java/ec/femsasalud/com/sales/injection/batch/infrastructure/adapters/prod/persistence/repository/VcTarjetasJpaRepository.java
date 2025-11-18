package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.VcTarjetasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VcTarjetasJpaRepository extends JpaRepository<VcTarjetasEntity, String> {

   VcTarjetasEntity findByNumeroTarjeta(String numeroTarjeta);

}
