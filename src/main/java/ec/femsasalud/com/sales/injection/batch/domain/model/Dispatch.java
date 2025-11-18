package ec.femsasalud.com.sales.injection.batch.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Dispatch {
    private String className;
    private String accessKey;
    private String cashier;
    private String cashierDocumentType;
    private String chain;
    private String channel;
    private String company;
    private LocalDateTime countableDate;
    private String document;
    private String documentType;
    private String orderNumber;
    private String orderSource;
    private boolean rounded;
    private BigDecimal total;
    private String transactionNumber;
}
