package ec.femsasalud.com.sales.injection.batch.shared.common;

public enum ParametroKey {
    URL_WS_CANCELACION("url_ws_cancelacion"),
    SCHEDULER_SEGUNDOS("scheduler_segundos"),
    SCHEDULER_MINUTOS("scheduler_minutos"),
    SCHEDULER_HORAS("scheduler_horas"),
    USER_FARMACIA_REQUEST("user_farmacia_request"),
    PASSWORD_FARMACIA_REQUEST("password_farmacia_request"),
    SID_AMBIENTE("sid_ambiente"),
    SID_REGION("sid_region"),
    DIRECTORIO_AMBIENTE_FARMACIA("directorio_ambiente_farmacia"),
    DIRECTORIO("directorio"),
    NUMERO_INTENTOS_SCHEDULER("numero_intentos_scheduler"),
    STORE_CODE_MDT("STORE_CODE_MDT"),
    ESTADO_FACTURA("estado_factura"),
    IVA_FACTURADOR("iva_facturador"),
    CODIGO_RESPUESTA_WS_CANCELACIONES("codigo_respuesta_ws_cancelaciones"),
    USUARIO_AED("usuario_aed"),
    PLAN_CREDITO("plan_credito"),
    URL_WS_FINALIZAR_TRANSACCION("url_ws_finalizar_transaccion"),
    X_IBM_CLIENT_ID("X-IBM-Client-Id"),
    NUMERO_CAMPANIA("numero_campania"),
    SERVICE_URL_RETURN("service_url_return"),
    SERVICE_URL_SALE("service_url"),
    SERVICE_URL_BONUS("service_url_bonus"),
    SERVICE_USER_LOGIN("service_user_login"),
    SERVICE_USER_LOGIN_USERNAME("service_user_login_username"),
    USER_WEB("user_web"),
    SERVICE_USER_LOGIN_PASSWORD("service_user_login_password"),

    // Parámetros SRI
    SRI_WSDL_AUTORIZACION_URL("sri_wsdl_autorizacion_url"),
    SRI_AMBIENTE("sri_ambiente"),
    SRI_XML_STORAGE_PATH("sri_xml_storage_path"),
    SRI_SCHEDULER_CRON("sri_scheduler_cron");

    private final String clave;

    ParametroKey(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public static ParametroKey fromClave(String clave) {
        for (ParametroKey key : values()) {
            if (key.clave.equals(clave)) {
                return key;
            }
        }
        throw new IllegalArgumentException("Parámetro no encontrado: " + clave);
    }
}
