package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoBillData {
    private String invoiceClientId;
    private String invoiceClientDocument;
    private String invoiceClientName;
    private String invoiceClientAddress;
    private String invoiceClientEmail;
    private String serialSRI;
    private String resolutionNumber;
    private String billUrl;
    private String xmlUrl;
}
