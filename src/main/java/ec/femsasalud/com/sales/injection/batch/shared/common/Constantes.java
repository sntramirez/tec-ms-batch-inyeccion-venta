package ec.femsasalud.com.sales.injection.batch.shared.common;

import java.math.BigDecimal;
import java.util.Set;

public class Constantes {

    // Tipos de datos para validación
    public static final String T_STRING = "STRING";
    public static final String T_NUMBER = "NUMBER";
    public static final String T_ARRAY = "ARRAY";
    public static final String T_OBJECT = "OBJECT";
    public static final String T_BOOLEAN = "BOOLEAN";

    // Códigos de documentos
    public static final String CODIGO_DOCUMENTO_F = "BILL";
    public static final String CODIGO_DOCUMENTO_NC = "CREDIT_NOTE_BILL";

    // Códigos de respuesta
    public static final String CODIGO_INICIAL = "100";
    public static final String CODIGO_EXITO = "200";
    public static final String CODIGO_ERROR_VALIDACION = "401";
    public static final String CODIGO_ERROR_DUPLICADO = "402";
    public static final String CODIGO_ERROR_SERVIDOR = "500";

    // Campos requeridos
    public static final String CAMPO_REQUERIDO = "S";
    public static final String CAMPO_OPCIONAL = "N";

    // Tipos de pago
    public static final String TIPO_PAGO_P2P = "P2P";
    public static final String TIPO_PAGO_CONV = "CONV";
    public static final String TIPO_PAGO_TCR = "TCR";

    // Mensajes de error comunes
    public static final String MSG_ERROR_CAMPO_REQUERIDO = "Campo requerido no encontrado";
    public static final String MSG_ERROR_TIPO_INVALIDO = "Tipo de dato inválido";
    public static final String MSG_ERROR_FORMATO_INVALIDO = "Formato inválido";
    public static final String MSG_ERROR_CONVERSION_JSON = "Error al convertir a JSON";

    // Códigos de estado
    public static final String SUCCESS_CODE = "200";
    public static final String ALTERNATIVE_SUCCESS_CODE = "0";
    public static final String ERROR_CODE = "500";

    // Mensajes de error de procesamiento
    public static final String VALIDATION_ERROR_MESSAGE = "Error en la validación de la solicitud";
    public static final String PROCESSING_ERROR_MESSAGE = "Error procesando el documento";
    public static final String UNEXPECTED_ERROR_MESSAGE = "Error inesperado en el procesamiento";

    public static final String NULL_BILL_REQUEST_ERROR_MESSAGE = "La solicitud de factura no puede ser nula";
    public static final String INVALID_DOCUMENT_TYPE_ERROR_MESSAGE = "Tipo de documento no válido o no soportado";

    // Valores de sistema
    public static final String SYSTEM_USER = "tec-ms-iny-venta";
    public static final String NO_REINTEGRATION = "N";
    public static final String ERROR_FLAG = "1";
    
    // Valores por defecto
    public static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ONE;
    public static final String DEFAULT_LOG_DESCRIPTION = "Log de registro factura";
    public static final String DEFAULT_USER_INIT = "interceptor";
    
    // Formatos de fecha
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_T_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String PAYMENT_CNV = "CNV";
    public static final String PAYMENT_P2P = "P2P";
    public static final String PAYMENT_METHOD_EFE = "EFE";
    public static final String PAYMENT_METHOD_CHQ = "CHQ";
    public static final Set<String> ELIGIBLE_PAYMENT_METHODS = Set.of(PAYMENT_METHOD_EFE, PAYMENT_METHOD_CHQ);
}
