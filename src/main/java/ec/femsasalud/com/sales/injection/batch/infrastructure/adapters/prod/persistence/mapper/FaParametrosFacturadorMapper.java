package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaParametrosFacturador;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaParametrosFacturadorEntity;
import org.springframework.stereotype.Component;

@Component
public class FaParametrosFacturadorMapper {

    public FaParametrosFacturador toDomain(FaParametrosFacturadorEntity entity) {
        if (entity == null) {
            return null;
        }
        return new FaParametrosFacturador(
                entity.getId(),
                entity.getClave(),
                entity.getValor()
        );
    }



}
