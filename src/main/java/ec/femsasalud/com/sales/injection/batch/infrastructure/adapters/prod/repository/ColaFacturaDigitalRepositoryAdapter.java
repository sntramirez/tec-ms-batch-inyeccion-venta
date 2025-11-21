package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.repository;

import ec.femsasalud.com.sales.injection.batch.domain.repository.ColaFacturaDigitalRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository.FaColaFacturaDigitalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Adaptador que implementa el repositorio de cola de facturas digitales
 */
@Repository
@RequiredArgsConstructor
public class ColaFacturaDigitalRepositoryAdapter implements ColaFacturaDigitalRepository {

    private final FaColaFacturaDigitalJpaRepository jpaRepository;

    @Override
    public List<FaColaFacturaDigitalEntity> findPendingDigitalInvoices() {
        return jpaRepository.findPendingDigitalInvoices();
    }

    @Override
    public FaColaFacturaDigitalEntity save(FaColaFacturaDigitalEntity entity) {
        return jpaRepository.save(entity);
    }
}
