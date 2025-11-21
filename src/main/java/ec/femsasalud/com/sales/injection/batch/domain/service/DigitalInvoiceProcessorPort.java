package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.sri.RespuestaAutorizacion;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;

/**
 * Puerto para procesar y registrar facturas digitales
 */
public interface DigitalInvoiceProcessorPort {

    /**
     * Procesa y guarda una factura o nota de crédito en las tablas correspondientes
     * @param colaFactura Registro de la cola de facturas digitales
     * @param autorizacion Autorización recibida del SRI
     * @param xmlFilePath Ruta donde se guardó el archivo XML
     */
    void processAndSaveInvoice(
        FaColaFacturaDigitalEntity colaFactura,
        RespuestaAutorizacion.Autorizacion autorizacion,
        String xmlFilePath
    );
}
