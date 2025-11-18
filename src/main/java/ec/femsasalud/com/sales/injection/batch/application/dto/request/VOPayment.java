package ec.femsasalud.com.sales.injection.batch.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VOPayment(
        String status,
        String payment,
        String paymentMethod,
        String sequencer,
        BigDecimal amount,
        BigDecimal interest,
        String quota,
        String quotaType,
        String authorizationType,
        String authorizationCode,
        String clientOwnerDocument,
        BigDecimal amountQuota,
        String customerDocNumber,
        String customerDocType,
        String email,
        String creditCardName,
        BigDecimal totalAmount,
        String description,
        @JsonProperty("Convenio")
        String convenio,
        @JsonProperty("NumeroTarjeta")
        String numeroTarjeta,
        @JsonProperty("AutorizacionCreditoConvenio")
        String autorizacionCreditoConvenio,
        @JsonProperty("IdHolder")
        String idHolder,

        String creditCardNumber,
        String creditCardFirstName,
        String authorizationBulletin,
        Date expirationDate,
        String bin,
        String groupcode,
        boolean abf,
        String abfTipo,
        String fecha,
        String codigoAseguradora,
        String contrato,
        String crAutnumcont,
        String deAutcodigo,
        String diagnostico,
        String diagnosticoNombre,
        String direccion,
        String emailAseguradora,
        String identificacionBeneficiario,
        String identificacionTitular,
        String medico,
        String nombreBeneficiario,
        String nombreTitular,
        String pagoCliente,
        String pagoCopania,
        String plan,
        String planDescripcion,
        String razonSocial,
        String rucAseguradora,
        String tiCodigo,
        String uuid,
        String idBeneficiario,
        @JsonProperty("ti_numeroContrato")
        String tiNumeroContrato,
        @JsonProperty("ti_contrato")
        String tiContrato,
        @JsonProperty("ti_region")
        String tiRegion,
        @JsonProperty("ti_producto")
        String tiProducto,
        @JsonProperty("ti_numeroPersonaBeneficiario")
        String tiNumeroPersonaBeneficiario,
        @JsonProperty("ti_codigoTitular")
        String tiCodigoTitular,
        @JsonProperty("ti_codigoCobertura")
        String tiCodigoCobertura,
        String franchise
) {}
