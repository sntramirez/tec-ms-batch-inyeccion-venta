package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaIdentificacionClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaIdentificacionClienteJpaRepository extends JpaRepository<FaIdentificacionClienteEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FaIdentificacionClienteEntity f WHERE f.codigo = :idTypeClient")
    Boolean obtenerIdentificacion(@Param("idTypeClient") String idTypeClient);
}
