package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.DTOAuthorizedItem;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FarmaciaRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.DTOService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.QgUsers;
import org.springframework.stereotype.Component;

@Component
public class FarmaciaAdapterRepository implements FarmaciaRepository {
    @Override
    public QgUsers getQgUsers(String user) {
        return null;
    }

    @Override
    public DTOAuthorizedItem getAuthorizedItem(long l, long l1) {
        return null;
    }

    @Override
    public DTOService getServiceItem(long l) {
        return null;
    }
}
