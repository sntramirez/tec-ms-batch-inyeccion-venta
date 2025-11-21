package ec.femsasalud.com.sales.injection.batch.domain.service;

/**
 * Puerto para almacenar archivos en el filesystem
 */
public interface FileStoragePort {

    /**
     * Guarda un archivo XML en el sistema de archivos
     * @param xmlContent Contenido XML a guardar
     * @param claveAcceso Clave de acceso del comprobante
     * @param documentType Tipo de documento (CREDIT_NOTE_BILL, etc)
     * @return Ruta completa donde se guardó el archivo
     */
    String saveXmlFile(String xmlContent, String claveAcceso, String documentType);
}
