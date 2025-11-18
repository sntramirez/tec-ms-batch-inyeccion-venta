package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaColaFacturaDigital;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;
import org.springframework.stereotype.Component;

@Component
public class FaColaFacturaDigitalMapper {
    public FaColaFacturaDigitalEntity toEntity(FaColaFacturaDigital domain) {
        if (domain == null) return null;

        return FaColaFacturaDigitalEntity.builder()
                .id(domain.getId())
                .codigo(domain.getCodigo())
                .error(domain.getError())
                .fechaInserta(domain.getFechaInserta())
                .intentos(domain.getIntentos())
                .json(domain.getJson())
                .mensaje(domain.getMensaje())
                .orderId(domain.getOrderId())
                .reintegrar(domain.getReintegrar())
                .usuarioInserta(domain.getUsuarioInserta())
                .fechaActualiza(domain.getFechaActualiza())
                .usuarioActualiza(domain.getUsuarioActualiza())
                .businessDate(domain.getBusinessDate())
                .claveAcceso(domain.getClaveAcceso())
                .documentType(domain.getDocumentType())
                .build();
    }


}
