package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaColaFacturaDigital;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FaColaFacturaDigitalRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper.FaColaFacturaDigitalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FaColaFacturaDigitalAdapterRepository implements FaColaFacturaDigitalRepository {

    private final FaColaFacturaDigitalJpaRepository tbFacturasJpaRepository;
    private final FaColaFacturaDigitalMapper faColaFacturaDigitalMapper;


    @Override
    public void insertarFaColaFacturaDigital(FaColaFacturaDigital faColaFacturaDigital) {
        tbFacturasJpaRepository.save(faColaFacturaDigitalMapper.toEntity(faColaFacturaDigital));
    }
}
