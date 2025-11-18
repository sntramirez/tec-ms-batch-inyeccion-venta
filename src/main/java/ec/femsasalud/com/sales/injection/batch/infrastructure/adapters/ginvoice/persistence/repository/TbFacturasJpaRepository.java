package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity.TbFactura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TbFacturasJpaRepository extends JpaRepository<TbFactura, Long> {
    TbFactura findByClaveAcceso(String claveAcceso);
}
