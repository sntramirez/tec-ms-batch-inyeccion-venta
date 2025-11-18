package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoFactura {
    private String businessDate;
    private String correlationTX;
    private CoBillData billData;
    private CoClient client;
    private List<CoPayment> payments;
    private List<CoProduct> products;
    private String emittedDocument;
    private String chain;
    private String company;
    private String localId;
    private int posNumber;
    private String document;
    private String ticketnumber;
    private BigDecimal total;
    private int rounded;
    private String orderId;
    private String cashier;
    private String cashierDocumentType;
    private String cashierDocument;
    private String channel;
    private String accessKey;


}

