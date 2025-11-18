package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoGroup;

import java.util.List;

public interface FaFarmaciaRepository {
    List<CoGroup> getQgGroups(String clientId);
}
