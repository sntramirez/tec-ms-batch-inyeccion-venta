package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper;

import ec.femsasalud.com.sales.injection.batch.domain.model.LogFactura;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaLogFacturaDigitalEntity;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;

@Component
@Slf4j
public class FaLogFacturaDigitalMapper {

    public FaLogFacturaDigitalEntity toEntity(LogFactura logFactura) {
        FaLogFacturaDigitalEntity faLogFacturaDigitalEntity = new FaLogFacturaDigitalEntity();
        faLogFacturaDigitalEntity.setCodigo(logFactura.code());
        faLogFacturaDigitalEntity.setError(logFactura.error());
        faLogFacturaDigitalEntity.setFechaInserta(new Date(System.currentTimeMillis()));
        faLogFacturaDigitalEntity.setIntentos(logFactura.intentos());
        faLogFacturaDigitalEntity.setJson(logFactura.json());
        faLogFacturaDigitalEntity.setMensaje(logFactura.mensaje());
        faLogFacturaDigitalEntity.setOrderId(logFactura.orderId());
        faLogFacturaDigitalEntity.setReintegrar(logFactura.reintegrar());
        faLogFacturaDigitalEntity.setUsuarioInserta(logFactura.usuarioInserta());
        faLogFacturaDigitalEntity.setBusinessDate(logFactura.businessDate());
        faLogFacturaDigitalEntity.setDocumentType(logFactura.documentType());
        return faLogFacturaDigitalEntity;
    }


    public LogOrdenErrorDTO mapTupleToLogOrdenErrorDTO(Tuple tuple) {
        LogOrdenErrorDTO dto = new LogOrdenErrorDTO();

        try {
            // Mapeo de campos regulares
            dto.setCodigoId(convertToLong(tuple.get("codigo_id")));
            dto.setId(convertToLong(tuple.get("ID")));
            dto.setOrderId(tuple.get("ORDER_ID", String.class));
            dto.setIntentos(convertToLong(tuple.get("INTENTOS")));
            dto.setFechaInserta(tuple.get("FECHA_INSERTA", java.util.Date.class));
            dto.setCodigo(tuple.get("codigo", String.class));
            dto.setMensaje(tuple.get("mensaje", String.class));
            dto.setUsuarioInserta(tuple.get("usuario_inserta", String.class));

            // Manejo especial para campos CLOB
            Object jsonObj = tuple.get("JSON");
            dto.setJson(convertClobToString(jsonObj));

            Object logJsonObj = tuple.get("log_json");
            dto.setLogJson(convertClobToString(logJsonObj));

        } catch (Exception e) {
            log.error("Error al mapear Tuple a LogOrdenErrorDTO: {}", e.getMessage(), e);
        }

        return dto;
    }

    private Long convertToLong(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return Long.parseLong(obj.toString());
    }

    private String convertClobToString(Object obj) {
        if (obj == null) {
            return null;
        }

        // Si ya es un String, devolverlo directamente
        if (obj instanceof String) {
            return (String) obj;
        }

        // Manejar casos de CLOB
        if (obj instanceof Clob) {
            try {
                Clob clob = (Clob) obj;
                int length = (int) clob.length();
                if (length > 0) {
                    return clob.getSubString(1, length);
                }
                return "";
            } catch (Exception e) {
                log.error("Error al convertir Clob a String: {}", e.getMessage(), e);
                return "";
            }
        }

        // Para otros tipos, convertir a String
        return obj.toString();
    }
}
