package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FaColaFacturaDigitalJpaRepository extends JpaRepository<FaColaFacturaDigitalEntity, BigDecimal> {

    @Query("""
       SELECT f
       FROM FaColaFacturaDigitalEntity f
       WHERE f.codigo = '200'
       AND f.claveAcceso IS NOT NULL
       AND (f.usuarioActualiza IS NULL OR f.usuarioActualiza <> 'SRI_BATCH')
       AND (f.error IS NULL OR f.error <> 'SRI' OR f.intentos IS NULL OR f.intentos < 3)""")
    List<FaColaFacturaDigitalEntity> findPendingDigitalInvoices();

    @Query("""
       SELECT f
       FROM FaColaFacturaDigitalEntity f
       WHERE f.error = 'SRI'
       AND f.intentos >= 3""")
    List<FaColaFacturaDigitalEntity> findInvoicesWithSriError();
}
