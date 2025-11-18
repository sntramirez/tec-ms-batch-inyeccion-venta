package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.repository.FaFarmaciaRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FaFarmaciaAdapterRepository implements FaFarmaciaRepository {

    @Override
    public List<CoGroup> getQgGroups(String clientId) {
        return List.of();
    }
}
