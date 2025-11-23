package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaColaFacturaDigitalEntity;

import java.util.List;

/**
 * Puerto (repositorio) para acceder a la cola de facturas digitales
 */
public interface ColaFacturaDigitalRepository {

    /**
     * Encuentra facturas digitales pendientes de procesar
     * Excluye facturas con error 'S' (SRI) y 3 o más intentos
     * @return Lista de facturas con CODIGO=200 y CLAVE_ACCESO not null
     */
    List<FaColaFacturaDigitalEntity> findPendingDigitalInvoices();

    /**
     * Encuentra facturas con error 'S' (SRI) y 3 o más intentos
     * @return Lista de facturas con error SRI
     */
    List<FaColaFacturaDigitalEntity> findInvoicesWithSriError();

    /**
     * Guarda o actualiza una factura digital en la cola
     * @param entity Entidad a guardar
     * @return Entidad guardada
     */
    FaColaFacturaDigitalEntity save(FaColaFacturaDigitalEntity entity);

    /**
     * Guarda múltiples facturas digitales en la cola
     * @param entities Lista de entidades a guardar
     * @return Lista de entidades guardadas
     */
    List<FaColaFacturaDigitalEntity> saveAll(List<FaColaFacturaDigitalEntity> entities);
}
