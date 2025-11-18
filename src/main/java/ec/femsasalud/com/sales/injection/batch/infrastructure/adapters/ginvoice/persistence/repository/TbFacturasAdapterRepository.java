package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.repository.TbFacturaRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TbFacturasAdapterRepository implements TbFacturaRepository {

    private final TbFacturasJpaRepository tbFacturasJpaRepository;

    @Override
    public TbFactura existeFactura(String claveAcceso) {
        return tbFacturasJpaRepository.findByClaveAcceso(claveAcceso);
    }

    @Override
    public TbFactura crearFactura(Object factura) {


        return null;
    }
}
