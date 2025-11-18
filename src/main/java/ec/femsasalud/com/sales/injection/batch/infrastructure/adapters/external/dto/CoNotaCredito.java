package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoNotaCredito {

            private String correlationTX;
            private String correlativeAssociatedTX;
            private String orderId;
            private String businessDate;
            private String emittedDocument;
            private Boolean electronicDocument;
            private String document;
            private String localId;
            private String cashier;
            private String motive;
            private String bonusNumber;
            private String promotionBI;
}
