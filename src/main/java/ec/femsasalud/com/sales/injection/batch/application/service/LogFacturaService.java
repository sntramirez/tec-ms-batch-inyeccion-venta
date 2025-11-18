package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogFactura;
import ec.femsasalud.com.sales.injection.batch.domain.repository.LogFacturaRepository;
import ec.femsasalud.com.sales.injection.batch.shared.common.Constantes;
import ec.femsasalud.com.sales.injection.batch.shared.common.DateUtil;
import ec.femsasalud.com.sales.injection.batch.shared.common.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogFacturaService {

    private final LogFacturaRepository logFacturaRepository;

    /**
     * Guarda un log de factura con los datos proporcionados.
     *
     * @param voFactura Objeto que contiene los datos de la factura
     */
    public void saveFacturaLog(VOFactura voFactura) {
        LogFactura logFactura = createLogFacturaFromRequest(voFactura);
        logFacturaRepository.insertLogFactura(logFactura);
    }

    /**
     * Guarda un log de factura utilizando datos de la respuesta y la factura.
     *
     * @param response  Objeto que contiene la respuesta del procesamiento
     * @param voFactura Objeto que contiene los datos de la factura
     */
    public void saveFacturaLog(VOResponse response, VOFactura voFactura) {
        LogFactura logFactura = createLogFacturaFromRequest(response, voFactura);
        logFacturaRepository.insertLogFactura(logFactura);
    }

    /**
     * Guarda un log de factura a partir de una cadena JSON que representa una factura.
     *
     * @param requestJson Cadena JSON que contiene los datos de la factura
     */
    public void saveFacturaLog(String requestJson) {
        LogFactura logFactura = createLogFacturaFromRequest(requestJson);
        logFacturaRepository.insertLogFactura(logFactura);
    }

    /**
     * Crea un objeto LogFactura a partir de los datos de la factura.
     *
     * @param voFactura Objeto que contiene los datos de la factura
     * @return Objeto LogFactura creado
     */
    private LogFactura createLogFacturaFromRequest(VOFactura voFactura) {
        String jsonVoFactura = JsonUtil.convertirAJson(voFactura, Constantes.MSG_ERROR_CONVERSION_JSON);
        Date businessDate = DateUtil.convertirISOStringADate(voFactura.getBusinessDate(), new Date());

        LogFactura logFactura = new LogFactura(
                Constantes.CODIGO_EXITO,
                Constantes.CAMPO_REQUERIDO,
                Constantes.DEFAULT_AMOUNT,
                jsonVoFactura,
                Constantes.DEFAULT_LOG_DESCRIPTION,
                Constantes.NO_REINTEGRATION,
                voFactura.getUser(),
                voFactura.getOrderId(),
                businessDate,
                voFactura.getEmittedDocument()
        );

        return logFactura;
    }

    /**
     * Crea un objeto LogFactura a partir de los datos de la respuesta y la factura.
     *
     * @param response  Objeto que contiene la respuesta del procesamiento
     * @param voFactura Objeto que contiene los datos de la factura
     * @return Objeto LogFactura creado
     */
    private LogFactura createLogFacturaFromRequest(VOResponse response, VOFactura voFactura) {
        String jsonVoFactura = JsonUtil.convertirAJson(voFactura, Constantes.MSG_ERROR_CONVERSION_JSON);
        Date businessDate = DateUtil.convertirISOStringADate(voFactura.getBusinessDate(), new Date());

        LogFactura logFactura = new LogFactura(
                response.getCode(),
                Constantes.CAMPO_REQUERIDO,
                Constantes.DEFAULT_AMOUNT,
                jsonVoFactura,
                response.getMsg(),
                Constantes.NO_REINTEGRATION,
                voFactura.getUser(),
                voFactura.getOrderId(),
                businessDate,
                voFactura.getEmittedDocument()
        );

        return logFactura;
    }

    /**
     * Crea un objeto LogFactura a partir de una cadena JSON que representa una factura.
     *
     * @param requestJson Cadena JSON que contiene los datos de la factura
     * @return Objeto LogFactura creado
     */
    private LogFactura createLogFacturaFromRequest(String requestJson) {
        VOFactura voFactura = JsonUtil.convertirAObject(requestJson, VOFactura.class);

        // Si no se pudo convertir el JSON a VOFactura, creamos un log de error
        if (voFactura == null) {
            return new LogFactura(
                    Constantes.CODIGO_ERROR_VALIDACION,
                    Constantes.CAMPO_REQUERIDO,
                    Constantes.DEFAULT_AMOUNT,
                    requestJson,
                    "Error al convertir JSON a objeto VOFactura",
                    Constantes.NO_REINTEGRATION,
                    Constantes.DEFAULT_USER_INIT,
                    "",
                    new Date(),
                    ""
            );
        }

        Date businessDate = DateUtil.convertirISOStringADate(voFactura.getBusinessDate(), new Date());

        LogFactura logFactura = new LogFactura(
                Constantes.CODIGO_INICIAL,
                Constantes.CAMPO_REQUERIDO,
                Constantes.DEFAULT_AMOUNT,
                requestJson,
                Constantes.DEFAULT_LOG_DESCRIPTION,
                Constantes.NO_REINTEGRATION,
                Constantes.DEFAULT_USER_INIT,
                voFactura.getOrderId(),
                businessDate,
                voFactura.getEmittedDocument()
        );

        return logFactura;
    }
}
