package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;


import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaParametrosFacturadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface FaParametrosFacturadorJpaRepository extends JpaRepository<FaParametrosFacturadorEntity, BigDecimal> {
    FaParametrosFacturadorEntity findByClave(String clave);


    @Query("SELECT f FROM FaParametrosFacturadorEntity f WHERE f.clave IN :clave")
    List<FaParametrosFacturadorEntity> findByClaveIn(List<String> clave);
}
