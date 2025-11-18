package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.DTOAuthorizedItem;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.DTOService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.QgUsers;

public interface FarmaciaRepository {
    QgUsers getQgUsers(String user);

    DTOAuthorizedItem getAuthorizedItem(long l, long l1);

    DTOService getServiceItem(long l);
}
