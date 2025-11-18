package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity.TbOrganizacion;

public interface TbOrganizacionRepository {
    TbOrganizacion consultarOrganizacion(String ruc);
}
