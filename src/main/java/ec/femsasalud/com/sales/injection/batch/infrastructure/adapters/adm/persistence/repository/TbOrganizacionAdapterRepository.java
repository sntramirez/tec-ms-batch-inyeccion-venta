package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.repository;


import ec.femsasalud.com.sales.injection.batch.domain.repository.TbOrganizacionRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity.TbOrganizacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TbOrganizacionAdapterRepository implements TbOrganizacionRepository {

    private final TbOrganizacionJpaRepository tbOrganizacionJpaRepository;

    @Override
    public TbOrganizacion consultarOrganizacion(String ruc) {

        return tbOrganizacionJpaRepository.findByRuc(ruc);
    }
}
