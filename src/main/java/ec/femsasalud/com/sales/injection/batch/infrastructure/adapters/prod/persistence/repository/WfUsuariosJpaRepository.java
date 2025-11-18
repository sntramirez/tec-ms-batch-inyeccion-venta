package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.WfUsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WfUsuariosJpaRepository extends JpaRepository<WfUsuariosEntity, String> {
    
    @Query("SELECT p.identificacion FROM AbPersonasEntity p " +
           "JOIN WfUsuariosEntity u ON p.codigo = u.codigoPersona " +
           "WHERE u.nombreUsuario = :nombreUsuario")
    String findIdentificacionByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
}
