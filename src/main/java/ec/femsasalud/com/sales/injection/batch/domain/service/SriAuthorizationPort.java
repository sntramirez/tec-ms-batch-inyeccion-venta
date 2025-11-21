package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri.RespuestaAutorizacion;

/**
 * Puerto para consultar autorizaciones en el SRI (Servicio de Rentas Internas)
 */
public interface SriAuthorizationPort {

    /**
     * Consulta la autorización de un comprobante electrónico en el SRI
     * @param wsdlUrl URL del WSDL del SRI
     * @param claveAcceso Clave de acceso del comprobante
     * @return Respuesta con la autorización del SRI
     */
    RespuestaAutorizacion consultarAutorizacion(String wsdlUrl, String claveAcceso);

    /**
     * Obtiene el XML autorizado desde la respuesta del SRI
     * @param respuesta Respuesta de autorización del SRI
     * @return XML autorizado en formato String
     */
    String obtenerXmlAutorizado(RespuestaAutorizacion respuesta);
}
