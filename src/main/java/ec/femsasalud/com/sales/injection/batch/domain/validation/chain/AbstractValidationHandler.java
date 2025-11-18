package ec.femsasalud.com.sales.injection.batch.domain.validation.chain;


import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;


public abstract class AbstractValidationHandler implements ValidationHandler {
    private ValidationHandler nextHandler;

    @Override
    public ValidationHandler setNext(ValidationHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public VOResponse validate(VOFactura facturaRequest, VOResponse respuesta) {

        VOResponse result = doValidate(facturaRequest, respuesta);

        if (hasError(result)) {
            return result;
        }

        if (nextHandler != null) {
            return nextHandler.validate(facturaRequest, respuesta);
        }
        return result;
    }

    protected abstract VOResponse doValidate(VOFactura facturaRequest, VOResponse respuesta);

    private boolean hasError(VOResponse response) {
        return !"200".equals(response.getCode()) && !"0".equals(response.getCode());
    }
}
