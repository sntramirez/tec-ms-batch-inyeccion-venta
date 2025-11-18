package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity.TbOrganizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbOrganizacionJpaRepository extends JpaRepository<TbOrganizacion, Long> {
    TbOrganizacion findByRuc(String ruc);
}
