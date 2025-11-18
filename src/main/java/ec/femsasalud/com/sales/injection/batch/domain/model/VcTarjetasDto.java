package ec.femsasalud.com.sales.injection.batch.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record VcTarjetasDto(


        BigDecimal saldoCupo,


        BigDecimal porcentajeSobregiro,


        BigDecimal pagos,


        BigDecimal saldoConsumos,


        BigDecimal cupo,


        BigDecimal saldoFavor,


        BigDecimal debitos,


        BigDecimal saldoTarjeta,


        BigDecimal moraAcumulada,


        BigDecimal interesMora,


        BigDecimal creditos,


        BigDecimal saldoCuenta,


        BigDecimal cupoFarmacia,


        BigDecimal cupoRotativo,


        BigDecimal cupoAcumulado,


        BigDecimal costoTarjeta,


        BigDecimal costoEstadoCuenta,

        // Campos de fecha

        LocalDate fechaCaducidadPlastico,


        LocalDate fechaRetiro,


        LocalDate fechaRenovacion,


        LocalDate fechaPerdida,


        LocalDate fechaCaducidad,


        LocalDate fechaIngreso,


        LocalDate fechaCorte,


        LocalDate fechaCancelacion,


        LocalDate fechaEmision,


        LocalDate fechaProximoCorte,


        LocalDate fechaPago,


        LocalDate fechaVencimiento,


        LocalDateTime fechaActualizacion,


        LocalDate fechaEnvio,


        LocalDate fechaAnulacion,


        LocalDate fechaDuplicado,


        LocalDate fechaReactivacion,


        LocalDate campoFecha1,


        LocalDate campoFecha2,

        // Campos numéricos enteros

        Integer numeroDias,


        Integer duplicados,


        Integer renovaciones,


        Integer corte,


        Long campoNum1,


        Long campoNum2,

        // Campos de texto

        String estadoCuenta,


        String tipoCliente,


        String numeroTarjeta,


        String estadoTarjeta,


        String tarjetaPrincipal,


        String radicacionAdicional,


        String numeroRadicacion,


        String tarjetaPerdida,


        String productoVitalcard,


        String codigoPersona,


        String causalCancelacion,


        String debitoAutomatico,


        String numeroCuenta,


        String tipoCuenta,


        String banco,


        String convenio,


        String causalAnulacion,


        String causalModificacionCupo,


        String causalReactivacion,


        String centroCostosVitalcard,


        String tarjetaOrigen,


        String tarjetaCanjeada,


        String documento,


        String contrato,


        String codigoEmpleadoEmpresa,


        String activoFacturaCuenta,

        String traCorte,

        String firma,

        String calificacionCliente,

        String imprimeEstCta,

        String calificacionTarjeta,

        String campoVarc1,

        String campoVarc2,

        // Campos calculados

        BigDecimal saldoDisponible,

        Boolean activa,


        Boolean principal,

        Boolean tieneDebitoAutomatico
) {


}
