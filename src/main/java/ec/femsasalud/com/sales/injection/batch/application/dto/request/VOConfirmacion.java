package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@AllArgsConstructor
@Getter
@Setter
public class VOConfirmacion {

      private String correlationTX;
      private String date;
      private String operation;
      private String orderNumber;
      private List<VOPayments> payments;
}
