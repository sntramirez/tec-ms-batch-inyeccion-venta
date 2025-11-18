package ec.femsasalud.com.sales.injection.batch.domain.validation.chain;


import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;

public interface ValidationHandler {
    ValidationHandler setNext(ValidationHandler handler);
    VOResponse validate(VOFactura facturaRequest, VOResponse respuesta);
}
