package ec.femsasalud.com.sales.injection.batch.domain.validation.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.shared.common.Constantes;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;


@RequiredArgsConstructor
public class FieldValidationDomainService {

    private final ObjectMapper objectMapper;

    public VOResponse validateRequiredFields(String jsonString ,
                                             List<ValidItem> validationRules,
                                             VOResponse respuesta) {
        try {

            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String validationMessage = validateFields(jsonNode, validationRules);

            if (!validationMessage.isEmpty()) {
                return respuesta.setResponse(respuesta, "401", validationMessage);
            }

            return respuesta.setResponse(respuesta, "200", "Validación exitosa");

        } catch (IOException e) {
            return respuesta.setResponse(respuesta, "500",
                    "Error al procesar la estructura de datos para validación: " + e.getMessage());
        }
    }

    public VOResponse validateRequiredFieldsList(JsonNode jsonNode,
                                                 List<ValidItem> validationRules,
                                                 VOResponse respuesta,
                                                 String fieldContext) {

        if (!jsonNode.isArray()) {
            return respuesta.setResponse(respuesta, "401",
                    "El campo " + fieldContext + " debe ser un array");
        }


        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode arrayElement = jsonNode.get(i);
            String validationMessage = validateFields(arrayElement, validationRules);

            if (!validationMessage.isEmpty()) {
                return respuesta.setResponse(respuesta, "401",
                        validationMessage + " - " + fieldContext + "[" + i + "]");
            }
        }

        return respuesta;
    }


    public VOResponse validatePaymentFields(JsonNode paymentsNode,
                                            VOResponse respuesta,
                                            String fieldContext) {

        if (!paymentsNode.isArray()) {
            return respuesta.setResponse(respuesta, "401",
                    "El campo " + fieldContext + " debe ser un array");
        }

        for (int i = 0; i < paymentsNode.size(); i++) {
            JsonNode paymentNode = paymentsNode.get(i);

            // Validar campos base de pagos
            List<ValidItem> baseRules = buildBasePaymentRules();
            String baseValidation = validateFields(paymentNode, baseRules);

            if (!baseValidation.isEmpty()) {
                return respuesta.setResponse(respuesta, "401",
                        baseValidation + " - " + fieldContext + "[" + i + "]");
            }

            // Validar campos específicos según tipo de pago
            VOResponse specificValidation = validateSpecificPaymentFields(paymentNode, respuesta, i);
            if (!"200".equals(specificValidation.getCode()) && !"0".equals(specificValidation.getCode())) {
                return specificValidation;
            }
        }

        return respuesta;
    }

    /**
     * Valida campos específicos según el tipo de pago
     */
    private VOResponse validateSpecificPaymentFields(JsonNode paymentNode, VOResponse respuesta, int index) {
        JsonNode paymentTypeNode = paymentNode.get("payment");

        if (paymentTypeNode == null || paymentTypeNode.isNull()) {
            return respuesta;
        }

        String paymentType = paymentTypeNode.asText();

        return switch (paymentType.toUpperCase()) {
            case "P2P" -> validateP2PPayment(paymentNode, respuesta, index);
            case "CONV" -> validateConvPayment(paymentNode, respuesta, index);
            default -> respuesta;
        };
    }

    /**
     * Valida campos específicos para pagos P2P
     */
    private VOResponse validateP2PPayment(JsonNode paymentNode, VOResponse respuesta, int index) {
        List<ValidItem> p2pRules = buildP2PPaymentRules();
        String validationMessage = validateFields(paymentNode, p2pRules);

        if (!validationMessage.isEmpty()) {
            return respuesta.setResponse(respuesta, "401",
                    validationMessage + " - payments[" + index + "] (P2P)");
        }

        return respuesta;
    }

    /**
     * Valida campos específicos para pagos CONV
     */
    private VOResponse validateConvPayment(JsonNode paymentNode, VOResponse respuesta, int index) {
        List<ValidItem> convRules = buildConvPaymentRules();
        String validationMessage = validateFields(paymentNode, convRules);

        if (!validationMessage.isEmpty()) {
            return respuesta.setResponse(respuesta, "401",
                    validationMessage + " - payments[" + index + "] (CONV)");
        }

        return respuesta;
    }

    /**
     * Método principal que valida campos contra reglas
     *
     * @param jsonNode El nodo JSON a validar
     * @param validationRules Las reglas de validación
     * @return String con mensaje de error o vacío si no hay errores
     */
    private String validateFields(JsonNode jsonNode, List<ValidItem> validationRules) {
        StringJoiner requiredFieldsErrors = new StringJoiner(",");
        StringJoiner typeFieldsErrors = new StringJoiner(",");

        for (ValidItem validItem : validationRules) {
            JsonNode fieldNode = jsonNode.findValue(validItem.name());

            if (fieldNode == null || fieldNode.isNull()) {
                // Campo no existe o es null
                if (isRequiredField(validItem)) {
                    requiredFieldsErrors.add(validItem.name());
                }
            } else {
                // Campo existe, validar tipo y contenido
                if (!isValidFieldType(fieldNode, validItem)) {
                    typeFieldsErrors.add(validItem.name());
                }
            }
        }
        return buildErrorMessage(requiredFieldsErrors, typeFieldsErrors);
    }

    /**
     * Valida si un campo cumple con el tipo esperado
     */
    private boolean isValidFieldType(JsonNode fieldNode, ValidItem validItem) {
        String fieldValue = fieldNode.asText();

        return switch (validItem.type()) {
            case Constantes.T_NUMBER -> isValidNumber(fieldNode, fieldValue);
            case Constantes.T_STRING -> isValidString(fieldNode, fieldValue);
            case Constantes.T_ARRAY -> isValidArray(fieldNode);
            case Constantes.T_OBJECT -> isValidObject(fieldNode);
            case Constantes.T_BOOLEAN -> isValidBoolean(fieldNode);
            default -> true; // Tipo desconocido, asumir válido
        };
    }

    /**
     * Valida si un campo numérico es válido
     */
    private boolean isValidNumber(JsonNode fieldNode, String fieldValue) {
        // Si es un nodo numérico nativo, es válido
        if (fieldNode.isNumber()) {
            return true;
        }

        // Si es string, validar que sea numérico
       // return UtilFactura.isNumeric(fieldValue);
        return true;
    }

    /**
     * Valida si un campo string es válido
     */
    private boolean isValidString(JsonNode fieldNode, String fieldValue) {
        // String no puede estar vacío
        return fieldValue != null && !fieldValue.trim().isEmpty();
    }

    /**
     * Valida si un campo array es válido
     */
    private boolean isValidArray(JsonNode fieldNode) {
        return fieldNode.isArray() && fieldNode.size() > 0;
    }

    /**
     * Valida si un campo object es válido
     */
    private boolean isValidObject(JsonNode fieldNode) {
        return fieldNode.isObject() && fieldNode.size() > 0;
    }

    /**
     * Valida si un campo boolean es válido
     */
    private boolean isValidBoolean(JsonNode fieldNode) {
        return fieldNode.isBoolean();
    }

    /**
     * Verifica si un campo es requerido
     */
    private boolean isRequiredField(ValidItem validItem) {
        return "S".equalsIgnoreCase(validItem.required());
    }

    /**
     * Construye el mensaje de error final
     */
    private String buildErrorMessage(StringJoiner requiredFieldsErrors, StringJoiner typeFieldsErrors) {
        StringJoiner finalMessage = new StringJoiner(", ");

        if (requiredFieldsErrors.length() > 0) {
            finalMessage.add("Campos requeridos no encontrados (" + requiredFieldsErrors.toString() + ")");
        }

        if (typeFieldsErrors.length() > 0) {
            finalMessage.add("Campos no válidos encontrados (" + typeFieldsErrors.toString() + ")");
        }

        return finalMessage.toString();
    }

    // =====================================================
    // MÉTODOS PARA CONSTRUIR REGLAS DE VALIDACIÓN
    // =====================================================

    /**
     * Construye reglas base para pagos
     */
    private List<ValidItem> buildBasePaymentRules() {
        return List.of(
                new ValidItem("payment", Constantes.T_STRING, "S"),
                new ValidItem("paymentMethod", Constantes.T_STRING, "S"),
                new ValidItem("amount", Constantes.T_NUMBER, "S"),
                new ValidItem("interest", Constantes.T_NUMBER, "N"),
                new ValidItem("amountQuota", Constantes.T_NUMBER, "N"),
                new ValidItem("quota", Constantes.T_NUMBER, "N")
        );
    }

    /**
     * Construye reglas específicas para pagos P2P
     */
    private List<ValidItem> buildP2PPaymentRules() {
        return List.of(
                new ValidItem("authorizationCode", Constantes.T_STRING, "S"),
                new ValidItem("authorizationBulletin", Constantes.T_STRING, "S"),
                new ValidItem("interest", Constantes.T_NUMBER, "S"),
                new ValidItem("quota", Constantes.T_NUMBER, "S"),
                new ValidItem("creditCardFirstName", Constantes.T_STRING, "S"),
                new ValidItem("bin", Constantes.T_NUMBER, "S"),
                new ValidItem("groupcode", Constantes.T_STRING, "S")
        );
    }

    /**
     * Construye reglas específicas para pagos CONV
     */
    private List<ValidItem> buildConvPaymentRules() {
        return List.of(
                new ValidItem("numeroTarjeta", Constantes.T_STRING, "S"),
                new ValidItem("convenio", Constantes.T_STRING, "S")
        );
    }

    // =====================================================
    // MÉTODOS UTILITARIOS ADICIONALES
    // =====================================================

    /**
     * Valida formato de email
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Regex básico para validación de email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Valida formato de RUC ecuatoriano
     */
    public boolean isValidRuc(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }

        // Validación básica de RUC: solo números
        return ruc.matches("\\d{13}");
    }

    /**
     * Valida longitud mínima y máxima de un campo
     */
    public boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) {
            return false;
        }

        int length = value.length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Valida que un valor esté dentro de un rango numérico
     */
    public boolean isValidRange(double value, double min, double max) {
        return value >= min && value <= max;
    }
}
