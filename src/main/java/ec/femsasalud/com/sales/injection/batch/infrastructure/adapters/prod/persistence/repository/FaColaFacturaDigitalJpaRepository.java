package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FaColaFacturaDigitalJpaRepository extends JpaRepository<FaColaFacturaDigitalEntity, BigDecimal> {
}
