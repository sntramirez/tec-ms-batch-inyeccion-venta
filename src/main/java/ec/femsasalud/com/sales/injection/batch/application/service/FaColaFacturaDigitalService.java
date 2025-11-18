package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.domain.model.FaColaFacturaDigital;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FaColaFacturaDigitalRepository;
import ec.femsasalud.com.sales.injection.batch.shared.common.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static ec.femsasalud.com.sales.injection.batch.shared.common.Constantes.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaColaFacturaDigitalService {

    private final FaColaFacturaDigitalRepository faColaFacturaDigitalRepository;

    public void saveFaColaFactura(VOFactura document,String jsonDocument, VOResponse response) {
        log.info("Procesamiento exitoso para orderId: {} -> {}", document.getOrderId(), response.getMsg());

        FaColaFacturaDigital registro = createBaseRegistro(document,jsonDocument);
        registro.setMensaje(response.getMsg());
        registro.setCodigo(SUCCESS_CODE);
        registro.setError(CAMPO_OPCIONAL);

        saveRegistro(registro);
    }

    public void saveFaColaFactura(VOFactura document,String jsonDocument, String errorMessage) {
        log.error("Error al procesar orden de venta orderId: {}", document.getOrderId(), errorMessage);

        FaColaFacturaDigital registro = createErrorRegistro(document,jsonDocument, errorMessage);
        saveRegistro(registro);
    }

    private void saveRegistro(FaColaFacturaDigital registro) {
        try {
            faColaFacturaDigitalRepository.insertarFaColaFacturaDigital(registro);
            log.debug("Registro guardado exitosamente para orderId: {}", registro.getOrderId());
        } catch (Exception e) {
            log.error("Error al guardar registro para orderId: {}", registro.getOrderId(), e);
        }
    }

    private FaColaFacturaDigital createErrorRegistro(VOFactura document,String jsonDocument, String errorMessage) {

        FaColaFacturaDigital registro = createBaseRegistro(document,jsonDocument);
        registro.setError(ERROR_FLAG);
        registro.setMensaje(errorMessage);
        registro.setCodigo(ERROR_CODE);
        return registro;
    }

    private FaColaFacturaDigital createBaseRegistro(VOFactura document,String jsonDocument) {

        FaColaFacturaDigital registro = new FaColaFacturaDigital();

        registro.setOrderId(document.getOrderId());
        registro.setFechaInserta(getCurrentTimestamp());
        registro.setJson(jsonDocument);
        registro.setUsuarioInserta(SYSTEM_USER);
        registro.setReintegrar(NO_REINTEGRATION);
        registro.setIntentos(BigDecimal.ONE);
        registro.setBusinessDate(DateUtil.convertirISOStringADate(document.getBusinessDate()) );
        registro.setDocumentType(document.getEmittedDocument());
        registro.setClaveAcceso(document.getAuthorizationCode());
        return registro;
    }

    private Date getCurrentTimestamp() {
        return new Date();
    }
}
