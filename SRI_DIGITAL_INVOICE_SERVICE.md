# Microservicio de Procesamiento de Facturas Digitales SRI

## Descripción
Este microservicio consulta automáticamente el SRI (Servicio de Rentas Internas) para obtener la autorización de facturas electrónicas y notas de crédito, descarga los XMLs autorizados y los registra en las tablas correspondientes.

## Funcionalidades

### 1. Procesamiento Automático
- **Job Batch Programado**: Se ejecuta cada 30 minutos automáticamente
- **Consulta Inteligente**: Lee registros de `FA_COLA_FACTURA_DIGITAL` con:
  - `CODIGO = '200'` (pendientes de procesar)
  - `CLAVE_ACCESO IS NOT NULL`

### 2. Consulta al SRI
- Consulta el WSDL del SRI configurado en parámetros
- Obtiene la autorización usando la clave de acceso
- Valida que el comprobante esté autorizado

### 3. Almacenamiento de XML
- Descarga el XML autorizado
- Lo guarda en el filesystem parametrizado
- Estructura de carpetas: `{base_path}/{año}/{mes}/{tipo_documento}/`
- Nombres de archivo:
  - Facturas: `FAC_{clave_acceso}.xml`
  - Notas de Crédito: `NC_{clave_acceso}.xml`

### 4. Registro en Base de Datos
- **Facturas**: Se registran en `ginvoice.tb_factura`
- **Notas de Crédito**: Se registran en `ginvoice.TB_NOTA_CREDITO`
- El tipo de documento se determina por el campo `DOCUMENT_TYPE`:
  - `CREDIT_NOTE_BILL` → Nota de Crédito
  - Otros valores → Factura

### 5. Actualización de Estado
- **Éxito** (`CODIGO = '201'`):
  - Actualiza el mensaje con el número de autorización
  - Marca fecha y usuario de actualización
- **Error** (`CODIGO = '500'`):
  - Registra el mensaje de error
  - Mantiene el registro para revisión manual

## Configuración

### Configuración en Base de Datos

El microservicio utiliza **configuración exclusiva desde Base de Datos** para los parámetros del SRI:

1. **Base de Datos** (`FA_PARAMETROS_FACTURADOR`) - **ÚNICA FUENTE**

**IMPORTANTE:** Los parámetros del SRI **DEBEN** estar configurados en la tabla `FA_PARAMETROS_FACTURADOR`. El microservicio **NO** funcionará sin estos parámetros en base de datos.

#### Parámetros de Configuración

| Clave en BD | Descripción | Ejemplo |
|-------------|-------------|---------|
| `sri_wsdl_autorizacion_url` | URL del WSDL del SRI | `https://cel.sri.gob.ec/...` |
| `sri_ambiente` | Ambiente SRI (1=Pruebas, 2=Producción) | `1` o `2` |
| `sri_xml_storage_path` | Ruta base para almacenar XMLs | `/factelectro/documentos2015/...` |

#### URLs del SRI por Ambiente

| Ambiente | URL WSDL | sri_ambiente |
|----------|----------|--------------|
| **PRUEBAS** | `https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl` | `1` |
| **PRODUCCIÓN** | `https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl` | `2` |

### Configuración en Base de Datos

Para configurar los parámetros en la tabla `FA_PARAMETROS_FACTURADOR`:

```sql
-- Configurar URL del WSDL del SRI
INSERT INTO FARMACIAS.FA_PARAMETROS_FACTURADOR (ID, CLAVE, VALOR)
VALUES (SEQ_FA_PARAMETROS_FACTURADOR.NEXTVAL, 'sri_wsdl_autorizacion_url',
        'https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl');

-- Configurar ambiente del SRI (1=Pruebas, 2=Producción)
INSERT INTO FARMACIAS.FA_PARAMETROS_FACTURADOR (ID, CLAVE, VALOR)
VALUES (SEQ_FA_PARAMETROS_FACTURADOR.NEXTVAL, 'sri_ambiente', '2');

-- Configurar ruta de almacenamiento de XMLs
INSERT INTO FARMACIAS.FA_PARAMETROS_FACTURADOR (ID, CLAVE, VALOR)
VALUES (SEQ_FA_PARAMETROS_FACTURADOR.NEXTVAL, 'sri_xml_storage_path',
        '/factelectro/documentos2015/factelectro/documentos2015/factura/autorizado');

COMMIT;
```

**Ventajas de usar Base de Datos:**
- ✅ Cambios sin reiniciar la aplicación (cache se refresca cada 5 minutos)
- ✅ Configuración centralizada
- ✅ Fácil cambio entre ambientes de prueba y producción
- ✅ Auditoría de cambios
- ✅ No requiere modificar archivos de propiedades ni redeployar

### Perfiles de Aplicación

El microservicio soporta los siguientes perfiles para configurar bases de datos y otros parámetros del sistema:
- **dev**: Desarrollo
- **test**: Testing/QA
- **prod**: Producción

**Nota:** Los parámetros del SRI (WSDL, ambiente, storage path) se configuran **exclusivamente en base de datos**, no en archivos properties.

### Configuración del Scheduler

El job se ejecuta por defecto cada 30 minutos. Para modificar la frecuencia, edite el archivo:
`SriDigitalInvoiceScheduler.java`

```java
@Scheduled(cron = "0 */30 * * * *")  // Cada 30 minutos
```

**Ejemplos de cron expressions:**
- Cada 15 minutos: `0 */15 * * * *`
- Cada hora: `0 0 * * * *`
- Cada día a las 2 AM: `0 0 2 * * *`

## API REST Endpoints

### 1. Procesar Facturas Manualmente
```http
POST /batch-inyeccion-venta/api/sri/digital-invoice/process
```

**Respuesta exitosa:**
```json
{
  "status": "success",
  "message": "Procesamiento de facturas digitales completado exitosamente",
  "timestamp": 1234567890123
}
```

### 2. Ejecutar Batch Job Manualmente
```http
POST /batch-inyeccion-venta/api/sri/digital-invoice/batch/run
```

**Respuesta exitosa:**
```json
{
  "status": "success",
  "message": "Batch job iniciado exitosamente",
  "timestamp": 1234567890123
}
```

### 3. Health Check
```http
GET /batch-inyeccion-venta/api/sri/digital-invoice/health
```

**Respuesta:**
```json
{
  "status": "UP",
  "service": "SRI Digital Invoice Service",
  "timestamp": 1234567890123
}
```

## Flujo de Procesamiento

```
1. Scheduler ejecuta cada 30 minutos
   ↓
2. Lee FA_COLA_FACTURA_DIGITAL (CODIGO=200, CLAVE_ACCESO NOT NULL)
   ↓
3. Por cada registro:
   ├─ Consulta SRI con CLAVE_ACCESO
   ├─ Valida estado AUTORIZADO
   ├─ Descarga XML
   ├─ Guarda en filesystem
   ├─ Parsea XML
   ├─ Registra en tb_factura o TB_NOTA_CREDITO
   └─ Actualiza FA_COLA_FACTURA_DIGITAL (CODIGO=201)

En caso de error:
   └─ Actualiza FA_COLA_FACTURA_DIGITAL (CODIGO=500 con mensaje)
```

## Estructura de Directorios XML

La ruta base configurada es: `/factelectro/documentos2015/factelectro/documentos2015/factura/autorizado`

El servicio crea automáticamente subdirectorios por año, mes y tipo de documento:

```
/factelectro/documentos2015/factelectro/documentos2015/factura/autorizado/
├── 2025/
│   ├── 01/
│   │   ├── facturas/
│   │   │   ├── FAC_1234567890123456789012345678901234567890123.xml
│   │   │   └── FAC_9876543210987654321098765432109876543210987.xml
│   │   └── notas_credito/
│   │       └── NC_5555555555555555555555555555555555555555555.xml
│   └── 02/
│       ├── facturas/
│       └── notas_credito/
```

**Nota**: La estructura `{año}/{mes}/{tipo_documento}/` se crea automáticamente bajo la ruta base configurada.

## Códigos de Estado en FA_COLA_FACTURA_DIGITAL

| CODIGO | DESCRIPCIÓN |
|--------|-------------|
| `200` | Pendiente de procesar |
| `201` | Procesado exitosamente |
| `500` | Error en el procesamiento |

## Logs

Los logs del servicio se encuentran en:
- **Nivel**: INFO por defecto
- **Paquete**: `ec.femsasalud.com.sales.injection.batch`

Para habilitar logs de debug de WebClient, descomentar en `application.properties`:
```properties
logging.level.org.springframework.web.reactive.function.client.ExchangeFunctions=DEBUG
logging.level.reactor.netty.http.client.HttpClient=DEBUG
```

## Componentes Principales

### 1. SriDigitalInvoiceService
Servicio orquestador principal que coordina todo el proceso.

### 2. SriAutorizacionClient
Cliente SOAP que consulta el WSDL del SRI.

### 3. XmlFileStorageService
Gestiona el almacenamiento de XMLs en el filesystem.

### 4. DigitalInvoiceProcessorService
Procesa y registra facturas/notas de crédito en la base de datos.

### 5. SriDigitalInvoiceScheduler
Ejecuta el job automáticamente según el cron configurado.

## Consideraciones

1. **Configuración Obligatoria en BD**: Los parámetros del SRI **DEBEN** estar configurados en `FA_PARAMETROS_FACTURADOR`:
   - `sri_wsdl_autorizacion_url`
   - `sri_ambiente`
   - `sri_xml_storage_path`
2. **Conexión Internet**: El servicio requiere acceso a Internet para consultar el SRI
3. **Permisos de Escritura**: Asegurar permisos en el directorio configurado en `sri_xml_storage_path`
4. **Ambiente Correcto**: Verificar que el parámetro `sri_ambiente` corresponda al ambiente deseado:
   - Dev/Test deben usar ambiente PRUEBAS del SRI (`sri_ambiente=1`)
   - Producción debe usar ambiente PRODUCCIÓN del SRI (`sri_ambiente=2`)
5. **Reintentos**: Los registros con error (CODIGO=500) no se reprocesarán automáticamente
6. **Duplicados**: El servicio verifica por `CLAVE_ACCESO` antes de insertar
7. **URLs del SRI**: Las URLs de PRUEBAS y PRODUCCIÓN son diferentes:
   - PRUEBAS: `https://celcer.sri.gob.ec/...`
   - PRODUCCIÓN: `https://cel.sri.gob.ec/...`
8. **Cache de Parámetros**: Los parámetros se cachean y refrescan cada 5 minutos. Cambios en BD tardan hasta 5 minutos en aplicarse

## Troubleshooting

### El job no se ejecuta automáticamente
- Verificar que `@EnableScheduling` esté presente
- Revisar logs para errores de configuración

### Error al guardar XML
- Verificar permisos del directorio `XML_STORAGE_PATH`
- Verificar que el disco tenga espacio suficiente

### Error al consultar SRI
- Verificar conectividad a Internet
- Verificar que los parámetros `sri_wsdl_autorizacion_url` y `sri_ambiente` estén configurados en `FA_PARAMETROS_FACTURADOR`
- Verificar que la URL del WSDL corresponda al ambiente correcto (PRUEBAS vs PRODUCCIÓN)
- Revisar si el SRI está disponible
- Verificar que el valor de `sri_ambiente` sea correcto (1=Pruebas, 2=Producción)

### Registros no se procesan
- Verificar que `CODIGO = '200'`
- Verificar que `CLAVE_ACCESO IS NOT NULL`
- Revisar logs para errores específicos

## Ejemplo de Uso

1. Insertar registro en FA_COLA_FACTURA_DIGITAL:
```sql
INSERT INTO FARMACIAS.FA_COLA_FACTURA_DIGITAL
(ID, CODIGO, CLAVE_ACCESO, DOCUMENT_TYPE, FECHA_INSERTA, USUARIO_INSERTA)
VALUES
(SEQ_FA_LOG_FACTURA_DIGITAL.NEXTVAL, '200',
 '1234567890123456789012345678901234567890123',
 'INVOICE', SYSDATE, 'SISTEMA');
COMMIT;
```

2. El scheduler procesará automáticamente en la siguiente ejecución

3. O ejecutar manualmente:
```bash
curl -X POST http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/process
```

4. Verificar resultado:
```sql
SELECT ID, CODIGO, MENSAJE, FECHA_ACTUALIZA
FROM FARMACIAS.FA_COLA_FACTURA_DIGITAL
WHERE CLAVE_ACCESO = '1234567890123456789012345678901234567890123';
```
