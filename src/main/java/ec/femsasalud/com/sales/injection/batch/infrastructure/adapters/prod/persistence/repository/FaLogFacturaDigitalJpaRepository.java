package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.FaLogFacturaDigitalEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface FaLogFacturaDigitalJpaRepository extends JpaRepository<FaLogFacturaDigitalEntity, BigDecimal> {

    @Query(nativeQuery = true, value = """
        SELECT 
            rownum AS codigo_id, 
            resultado.ID,
            resultado.ORDER_ID,
            resultado.JSON, --clob
            resultado.INTENTOS,
            resultado.FECHA_INSERTA,
            resultado.codigo,
            resultado.mensaje,
            resultado.usuario_inserta,
            resultado.log_json --clob 
        FROM (
            SELECT
                a.ID,
                a.ORDER_ID AS ORDER_ID,
                a.JSON,
                a.INTENTOS,
                a.FECHA_INSERTA,
                '100' codigo,
                'No se envio a Geo' mensaje,
                a.usuario_inserta,
                null AS log_json
            FROM (
                SELECT ID, ORDER_ID, JSON, INTENTOS, FECHA_INSERTA, usuario_inserta, DOCUMENT_TYPE, FARMACIA, CHANNEL
                FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL
                WHERE codigo = 100
                    AND fecha_inserta >= TO_DATE('01-07-2024', 'dd-mm-yyyy')
                    AND CHANNEL IS NOT NULL
                ) a
                JOIN farmacias.fa_parametros_farmacia c
                    ON a.FARMACIA = c.FARMACIA  
                        AND c.campo = 'SISTEMA_GEO'
                        AND c.VALOR = 'S'
                        AND c.activo = 'S'
            WHERE a.order_id IN (
                SELECT order_id
                FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL
                WHERE codigo IN (100, 200)
                    AND fecha_inserta >= TO_DATE('01-07-2024', 'dd-mm-yyyy')
                GROUP BY order_id
                HAVING COUNT(order_id) < 2
            )
     
            UNION ALL
     
            SELECT
                a.ID,
                a.ORDER_ID,
                a.JSON,
                a.INTENTOS,
                a.FECHA_INSERTA,
                b.codigo,
                b.mensaje,
                a.usuario_inserta,
                b.json AS log_json
            FROM (
                SELECT ID, ORDER_ID, JSON, INTENTOS, FECHA_INSERTA, usuario_inserta, DOCUMENT_TYPE, FARMACIA, CHANNEL
                FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL
                WHERE codigo = 100
                    AND fecha_inserta >= TO_DATE('01-07-2024', 'dd-mm-yyyy')
                    AND CHANNEL IS NOT NULL
                ) a
                JOIN FARMACIAS.FA_COLA_FACTURA_DIGITAL b
                    ON a.order_id = b.order_id
                        AND b.codigo <> 200
                JOIN farmacias.fa_parametros_farmacia c
                    ON a.FARMACIA = c.FARMACIA  
                        AND c.campo = 'SISTEMA_GEO'
                        AND c.VALOR = 'S'
                        AND c.activo = 'S'
            WHERE NOT EXISTS (
                SELECT 1
                FROM FARMACIAS.FA_COLA_FACTURA_DIGITAL
                WHERE ORDER_ID = a.ORDER_ID
                    AND codigo = '200'
            )
     
            UNION ALL
     
            SELECT
                a.ID,
                a.ORDER_ID,
                a.JSON,
                a.INTENTOS,
                a.FECHA_INSERTA,
                b.CODIGO,
                b.MENSAJE,
                a.USUARIO_INSERTA,
                b.JSON AS LOG_JSON
            FROM (
                SELECT ID, ORDER_ID, JSON, INTENTOS, FECHA_INSERTA, USUARIO_INSERTA, DOCUMENT_TYPE, FARMACIA, CHANNEL
                FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL
                WHERE CODIGO = 100
                    AND FECHA_INSERTA >= TO_DATE('01-07-2024', 'dd-mm-yyyy')
                    AND DOCUMENT_TYPE = 'CREDIT_NOTE_BILL'
                ) a
                JOIN FARMACIAS.FA_COLA_FACTURA_DIGITAL b
                    ON a.ORDER_ID = b.ORDER_ID
                        AND b.CODIGO <> 200
                JOIN farmacias.fa_parametros_farmacia c
                    ON a.FARMACIA = c.FARMACIA  
                        AND c.campo = 'SISTEMA_GEO'
                        AND c.VALOR = 'S'
                        AND c.activo = 'S'
            WHERE NOT EXISTS (
                SELECT 1
                FROM FARMACIAS.FA_COLA_FACTURA_DIGITAL b
                WHERE b.ORDER_ID = a.order_id
                    AND b.codigo = 200
                    AND EXISTS (
                        SELECT 1
                        FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL l
                        WHERE l.ORDER_ID = b.ORDER_ID
                            AND l.DOCUMENT_TYPE = 'CREDIT_NOTE_INVOICE'
                    )
            )
     
            UNION ALL
     
            SELECT
                a.ID,
                a.ORDER_ID,
                a.JSON,
                a.INTENTOS,
                a.FECHA_INSERTA,
                '100' CODIGO,
                a.MENSAJE,
                a.USUARIO_INSERTA,
                NULL AS LOG_JSON
            FROM (
                SELECT ID, ORDER_ID, JSON, INTENTOS, FECHA_INSERTA, MENSAJE, USUARIO_INSERTA, DOCUMENT_TYPE, FARMACIA, CHANNEL
                FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL
                WHERE FECHA_INSERTA >= TO_DATE('01-07-2024', 'dd-mm-yyyy')
                    AND mensaje LIKE '%Plan de credito%'
                ) a
                JOIN farmacias.fa_parametros_farmacia c
                    ON a.FARMACIA = c.FARMACIA  
                        AND c.campo = 'SISTEMA_GEO'
                        AND c.VALOR = 'S'
                        AND c.activo = 'S'
            WHERE NOT EXISTS (
                SELECT 1
                FROM FARMACIAS.FA_COLA_FACTURA_DIGITAL b
                WHERE b.ORDER_ID = a.order_id
                    AND b.codigo = 200
                    AND EXISTS (
                        SELECT 1
                        FROM FARMACIAS.FA_LOG_FACTURA_DIGITAL l
                        WHERE l.ORDER_ID = b.ORDER_ID
                            AND l.DOCUMENT_TYPE = 'INVOICE'
                    )
            )
        ) resultado
        WHERE 1 = 1
    AND (:fechaDesde IS NULL OR resultado.FECHA_INSERTA >= :fechaDesde)
    AND (:fechaHasta IS NULL OR resultado.FECHA_INSERTA <= :fechaHasta)
""")
List<Tuple> obtenerOrdenesConErrorPorDefecto(
    @Param("fechaDesde") Date fechaDesde,
    @Param("fechaHasta") Date fechaHasta
);
}
