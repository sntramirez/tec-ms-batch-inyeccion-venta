package  ec.femsasalud.com.sales.injection.batch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOrdenErrorDTO {
    private Long codigoId;
    private Long id;
    private String orderId;
    private String json;
    private Long intentos;
    private Date fechaInserta;
    private String codigo;
    private String mensaje;
    private String usuarioInserta;
    private String logJson;
}
