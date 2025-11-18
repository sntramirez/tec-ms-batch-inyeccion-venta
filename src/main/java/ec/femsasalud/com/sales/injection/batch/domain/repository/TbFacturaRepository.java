package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbFactura;

public interface TbFacturaRepository {
    TbFactura existeFactura(String claveAcceso);
    TbFactura crearFactura(Object factura);
}
