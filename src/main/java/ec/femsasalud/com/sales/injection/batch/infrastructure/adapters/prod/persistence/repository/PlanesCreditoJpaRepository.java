package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.AdPlanCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanesCreditoJpaRepository extends JpaRepository<AdPlanCreditoEntity, String> {

    @Query("""
        SELECT pc.codigo 
        FROM AdPlanCreditoEntity pc 
        WHERE pc.tarjetaCredito IN (
            SELECT p.tarjeta 
            FROM AdPrefijoEntity p 
            WHERE p.prefijos = :prefijo 
            AND p.activo = 'S'
            AND p.tarjeta IN (
                SELECT tc.codigo 
                FROM AdPlanCreditoAdicionalEntity  pca, AdPlanCreditoEntity pc2, AdTarjetaCreditoEntity tc 
                WHERE pca.groupCode = :grupo 
                AND pc2.tarjetaCredito = tc.codigo 
                AND pca.planCredito = pc2.codigo 
                AND tc.emisor = 12
            )
        )
        AND (:cuota <= 1 OR pc.cuotasTarjetahabiente = :cuota)
        AND (:cuota > 1 OR pc.cuotasTarjetahabiente IN (0, 1))
        """)
    Optional<String> findPlanCreditByCriteria(
            @Param("prefijo") String prefijo,
            @Param("grupo") String grupo,
            @Param("cuota") Integer cuota
    );
}
