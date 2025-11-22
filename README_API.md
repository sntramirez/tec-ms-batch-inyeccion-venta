# API de Facturación Digital SRI

Microservicio para procesar facturas digitales consultando al SRI (Servicio de Rentas Internas de Ecuador).

## Endpoints Disponibles

### 1. Procesar Facturas Digitales

**POST** `/batch-inyeccion-venta/api/sri/digital-invoice/process`

Procesa todas las facturas digitales pendientes:
- Lee registros de `FA_COLA_FACTURA_DIGITAL` con `CODIGO='200'` y `CLAVE_ACCESO IS NOT NULL`
- Consulta autorización al SRI mediante WSDL
- Descarga y guarda XML completo con formato correcto (CDATA)
- Registra en `tb_factura` o `TB_NOTA_CREDITO` según tipo de documento
- Actualiza `USUARIO_ACTUALIZA='SRI_BATCH'` al finalizar

**Request:**
```bash
POST http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/process
Content-Type: application/json
```

**Response Exitosa:**
```json
{
  "status": "success",
  "message": "Procesamiento de facturas digitales completado exitosamente",
  "timestamp": 1700000000000
}
```

**Response con Error:**
```json
{
  "status": "error",
  "message": "Error al procesar facturas digitales: [detalle del error]",
  "timestamp": 1700000000000
}
```

### 2. Health Check

**GET** `/batch-inyeccion-venta/api/sri/digital-invoice/health`

Verifica que el servicio está activo.

**Response:**
```json
{
  "status": "UP",
  "service": "SRI Digital Invoice Service",
  "timestamp": 1700000000000
}
```

## Configuración

Todos los parámetros se obtienen de la tabla `FA_PARAMETROS_FACTURADOR`:

| Parámetro | Descripción | Ejemplo |
|-----------|-------------|---------|
| `sri_wsdl_autorizacion_url` | URL del WSDL del SRI para autorización | `https://celarios.sri.gob.ec/...` |
| `sri_ambiente` | Ambiente del SRI (1=Pruebas, 2=Producción) | `1` o `2` |
| `sri_xml_storage_path` | Ruta base para guardar XMLs | `/factelectro/documentos2015/...` |

### Validación de Parámetros

La aplicación valida al iniciar que los parámetros estén configurados. Si faltan, la aplicación **NO iniciará**.

## Procesamiento

### 1. Lectura de Registros

Query ejecutado:
```sql
SELECT f
FROM FA_COLA_FACTURA_DIGITAL f
WHERE f.codigo = '200'
AND f.claveAcceso IS NOT NULL
AND (f.usuarioActualiza IS NULL OR f.usuarioActualiza <> 'SRI_BATCH')
```

### 2. Consulta al SRI

- Se conecta al WSDL configurado
- Envía `claveAcceso` para consultar autorización
- Recibe respuesta XML con tag `<autorizacion>`

### 3. Guardado de XML

**Estructura de archivo:**
- Ruta: `{sri_xml_storage_path}/{ddMMyyyy}/{claveAcceso}.xml`
- Fecha extraída de los primeros 8 caracteres de `claveAcceso`
- Ejemplo: `/factelectro/.../05112025/0511202504179071031900110370140000006005658032311.xml`

**Formato del XML:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<autorizacion>
  <estado>AUTORIZADO</estado>
  <numeroAutorizacion>...</numeroAutorizacion>
  <fechaAutorizacion>2025-11-10T14:57:41-05:00</fechaAutorizacion>
  <ambiente>PRUEBAS</ambiente>
  <comprobante><![CDATA[<?xml version="1.0" encoding="UTF-8"?>
<factura id="comprobante" version="1.0.0">
  ...
</factura>]]></comprobante>
  <mensajes/>
</autorizacion>
```

### 4. Registro en Base de Datos

Según el `DOCUMENT_TYPE`:
- **Factura (01)**: Se registra en `tb_factura`
- **Nota de Crédito (04)**: Se registra en `TB_NOTA_CREDITO`

### 5. Actualización de Estado

En `FA_COLA_FACTURA_DIGITAL`:
- `CODIGO` se mantiene en `'200'` (sin cambios)
- `MENSAJE` se mantiene sin cambios
- `FECHA_ACTUALIZA` se actualiza con fecha actual
- `USUARIO_ACTUALIZA` = `'SRI_BATCH'`

Los registros procesados NO se vuelven a procesar gracias al filtro `usuarioActualiza <> 'SRI_BATCH'`.

## Arquitectura

El microservicio sigue **arquitectura hexagonal (Ports & Adapters)**:

### Domain Layer (Puertos)
- `SriAuthorizationPort` - Puerto para consultas al SRI
- `FileStoragePort` - Puerto para almacenamiento de archivos
- `DigitalInvoiceProcessorPort` - Puerto para procesamiento de facturas
- `ColaFacturaDigitalRepository` - Puerto para acceso a datos

### Application Layer (Casos de Uso)
- `ProcessDigitalInvoiceUseCase` - Orquesta el proceso completo

### Infrastructure Layer (Adaptadores)
- `SriAutorizacionClient` - Cliente SOAP para SRI
- `XmlFileStorageAdapter` - Almacenamiento en filesystem
- `DigitalInvoiceProcessorAdapter` - Procesamiento y guardado en BD
- `ColaFacturaDigitalRepositoryAdapter` - Acceso a JPA Repository

## Ejecución Programada

El proceso se ejecuta automáticamente cada 30 minutos mediante `@Scheduled`:

```java
@Scheduled(cron = "0 */30 * * * *")
public void scheduleProcessDigitalInvoices()
```

## Manejo de Errores

### Errores Comunes

**1. Parámetro no configurado**
```
IllegalStateException: La URL del WSDL del SRI debe estar configurada en FA_PARAMETROS_FACTURADOR
```
**Solución:** Configurar el parámetro faltante en `FA_PARAMETROS_FACTURADOR`

**2. Error al consultar SRI**
```
RuntimeException: Error al consultar autorización en el SRI
```
**Solución:** Verificar conectividad con el SRI y validez del WSDL URL

**3. Error de parseo XML**
```
RuntimeException: Error al parsear XML de respuesta del SRI
```
**Solución:** Verificar que la respuesta del SRI sea válida

### Logging

Nivel de logging configurado en `application.properties`:
```properties
logging.level.ec.femsasalud.com.sales.injection.batch=INFO
```

Para debug detallado:
```properties
logging.level.ec.femsasalud.com.sales.injection.batch=DEBUG
logging.level.org.springframework.web.reactive.function.client.ExchangeFunctions=DEBUG
```

## Testing

### Prueba Manual
```bash
# Ejecutar procesamiento
curl -X POST http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/process

# Health check
curl http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/health
```

### Verificar Resultados

1. **En FA_COLA_FACTURA_DIGITAL:**
```sql
SELECT * FROM FA_COLA_FACTURA_DIGITAL
WHERE USUARIO_ACTUALIZA = 'SRI_BATCH'
ORDER BY FECHA_ACTUALIZA DESC;
```

2. **XMLs Descargados:**
```bash
ls -la /factelectro/.../05112025/
```

3. **Registros en tb_factura / TB_NOTA_CREDITO:**
```sql
SELECT * FROM tb_factura
WHERE NUMERO_AUTORIZACION IS NOT NULL
ORDER BY ID DESC;
```

## Notas Importantes

- ✅ NO requiere tablas `BATCH_*` de Spring Batch
- ✅ Toda la configuración viene de `FA_PARAMETROS_FACTURADOR`
- ✅ Los XMLs se guardan con estructura CDATA correcta
- ✅ Evita reprocesar facturas ya procesadas
- ✅ Sigue arquitectura hexagonal
- ✅ Validación de parámetros al inicio
