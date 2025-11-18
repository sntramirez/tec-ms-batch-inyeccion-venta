package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoDetails {

     private String bin;
     private String quota;
     private String planCode;
     private String type;
     private String authorizationType;
     private BigDecimal interest;
     private String authorizationCode;
     private String cardNumber;
     private String clientId;
     private String clientOwnerDocument;
     private String accountNumber;
     private String franchise;
     private String clientName;
     private String encryptedCardNumber;
     private String onlineAuthorizationCode;
     private String magneticStripe;




}
