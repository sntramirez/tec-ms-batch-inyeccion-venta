# Configuración de Spring Batch en Oracle

## Problema
El endpoint `/api/sri/digital-invoice/batch/run` requiere tablas de Spring Batch que no existen en la base de datos:
- `BATCH_JOB_INSTANCE`
- `BATCH_JOB_EXECUTION`
- `BATCH_JOB_EXECUTION_PARAMS`
- `BATCH_STEP_EXECUTION`
- `BATCH_STEP_EXECUTION_CONTEXT`
- `BATCH_JOB_EXECUTION_CONTEXT`

## Solución

### Paso 1: Ejecutar el script SQL

Ejecuta el script `src/main/resources/schema-oracle-batch.sql` en tu base de datos Oracle en el **mismo schema** donde está configurado el datasource principal de la aplicación.

**Opciones para ejecutar:**

#### Opción A: SQL Developer / SQL Plus
```sql
-- Conéctate al schema correcto (ejemplo: FARMACIAS)
@src/main/resources/schema-oracle-batch.sql
```

#### Opción B: Desde línea de comandos
```bash
sqlplus usuario/password@database @src/main/resources/schema-oracle-batch.sql
```

### Paso 2: Verificar que las tablas se crearon

```sql
-- Verificar que existen las tablas
SELECT table_name
FROM user_tables
WHERE table_name LIKE 'BATCH_%'
ORDER BY table_name;

-- Deberías ver 6 tablas:
-- BATCH_JOB_EXECUTION
-- BATCH_JOB_EXECUTION_CONTEXT
-- BATCH_JOB_EXECUTION_PARAMS
-- BATCH_JOB_INSTANCE
-- BATCH_STEP_EXECUTION
-- BATCH_STEP_EXECUTION_CONTEXT
```

### Paso 3: Verificar las secuencias

```sql
-- Verificar que existen las secuencias
SELECT sequence_name
FROM user_sequences
WHERE sequence_name LIKE 'BATCH_%'
ORDER BY sequence_name;

-- Deberías ver 3 secuencias:
-- BATCH_JOB_EXECUTION_SEQ
-- BATCH_JOB_SEQ
-- BATCH_STEP_EXECUTION_SEQ
```

## Endpoints Disponibles

Una vez creadas las tablas, puedes usar ambos endpoints:

### 1. Endpoint Directo (NO requiere tablas BATCH_*)
```bash
POST http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/process
```
- Ejecuta directamente el UseCase
- NO registra historial en base de datos
- Más simple y directo

### 2. Endpoint con Spring Batch (REQUIERE tablas BATCH_*)
```bash
POST http://localhost:8080/batch-inyeccion-venta/api/sri/digital-invoice/batch/run
```
- Ejecuta a través de Spring Batch Job
- Registra historial de ejecuciones en BATCH_*
- Permite reintentos y manejo de errores avanzado

## Configuración

En `application.properties`:
```properties
# Spring Batch Configuration
spring.batch.job.enabled=false  # No ejecutar jobs automáticamente al startup
spring.batch.jdbc.initialize-schema=never  # NO crear tablas automáticamente (las creamos manualmente)
```

## ¿Qué schema usar?

Las tablas BATCH_* deben crearse en el **mismo schema** que tiene configurado tu `spring.datasource.url`.

Si tienes múltiples datasources, verifica cuál es el **primario** (@Primary) en tu configuración de JPA/Spring.

## Troubleshooting

### Error: ORA-00942: la tabla o vista no existe
- **Causa:** Las tablas BATCH_* no existen
- **Solución:** Ejecutar el script SQL en el schema correcto

### Error: ORA-01950: no existen privilegios sobre el tablespace
- **Causa:** El usuario no tiene permisos para crear objetos
- **Solución:** Contactar al DBA para otorgar permisos:
  ```sql
  GRANT CREATE TABLE TO usuario;
  GRANT CREATE SEQUENCE TO usuario;
  ```

### Error: Las tablas existen pero sigue dando error
- **Causa:** Las tablas están en un schema diferente
- **Solución:** Verificar el schema con:
  ```sql
  SELECT owner, table_name FROM all_tables WHERE table_name = 'BATCH_JOB_INSTANCE';
  ```

## Limpieza (Opcional)

Si necesitas recrear las tablas desde cero:

```sql
-- Ejecutar en orden para evitar errores de foreign keys
DROP TABLE BATCH_STEP_EXECUTION_CONTEXT CASCADE CONSTRAINTS;
DROP TABLE BATCH_JOB_EXECUTION_CONTEXT CASCADE CONSTRAINTS;
DROP TABLE BATCH_STEP_EXECUTION CASCADE CONSTRAINTS;
DROP TABLE BATCH_JOB_EXECUTION_PARAMS CASCADE CONSTRAINTS;
DROP TABLE BATCH_JOB_EXECUTION CASCADE CONSTRAINTS;
DROP TABLE BATCH_JOB_INSTANCE CASCADE CONSTRAINTS;

DROP SEQUENCE BATCH_STEP_EXECUTION_SEQ;
DROP SEQUENCE BATCH_JOB_EXECUTION_SEQ;
DROP SEQUENCE BATCH_JOB_SEQ;
```

Luego volver a ejecutar el script de creación.
