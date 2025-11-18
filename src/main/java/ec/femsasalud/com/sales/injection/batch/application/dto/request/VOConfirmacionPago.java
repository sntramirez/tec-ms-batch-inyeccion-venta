package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VOConfirmacionPago {
    private String correlationTx;
    private String storeCode;
    private String ordenNo;
}
