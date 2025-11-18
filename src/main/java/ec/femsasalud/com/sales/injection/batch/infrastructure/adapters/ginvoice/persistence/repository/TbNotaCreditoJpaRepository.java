package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbNotaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbNotaCreditoJpaRepository extends JpaRepository<TbNotaCredito, Long> {
    TbNotaCredito findByClaveAcceso(String claveAcceso);
}
