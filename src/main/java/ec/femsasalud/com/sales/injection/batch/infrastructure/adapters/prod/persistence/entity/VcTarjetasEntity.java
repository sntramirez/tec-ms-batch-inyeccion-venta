package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla VC_TARJETAS
 * Representa la información de tarjetas de crédito/débito del sistema
 */
@Entity
@Table(name = "VC_TARJETAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VcTarjetasEntity {

    @Id
    @Column(name = "NUMERO_TARJETA", length = 20)
    private String numeroTarjeta;

    @Column(name = "SALDO_CUPO", precision = 15, scale = 2)
    private BigDecimal saldoCupo;

    @Column(name = "PORCENTAJE_SOBREGIRO", precision = 5, scale = 2)
    private BigDecimal porcentajeSobregiro;

    @Column(name = "PAGOS", precision = 15, scale = 2)
    private BigDecimal pagos;

    @Column(name = "SALDO_CONSUMOS", precision = 15, scale = 2)
    private BigDecimal saldoConsumos;

    @Column(name = "CUPO", precision = 15, scale = 2)
    private BigDecimal cupo;

    @Column(name = "SALDO_FAVOR", precision = 15, scale = 2)
    private BigDecimal saldoFavor;

    @Column(name = "DEBITOS", precision = 15, scale = 2)
    private BigDecimal debitos;

    @Column(name = "SALDO_TARJETA", precision = 15, scale = 2)
    private BigDecimal saldoTarjeta;

    @Column(name = "MORA_ACUMULADA", precision = 15, scale = 2)
    private BigDecimal moraAcumulada;

    @Column(name = "INTERES_MORA", precision = 15, scale = 2)
    private BigDecimal interesMora;

    @Column(name = "CREDITOS", precision = 15, scale = 2)
    private BigDecimal creditos;

    @Column(name = "SALDO_CUENTA", precision = 15, scale = 2)
    private BigDecimal saldoCuenta;

    @Column(name = "CUPO_FARMACIA", precision = 15, scale = 2)
    private BigDecimal cupoFarmacia;

    @Column(name = "CUPO_ROTATIVO", precision = 15, scale = 2)
    private BigDecimal cupoRotativo;

    @Column(name = "CUPO_ACUMULADO", precision = 15, scale = 2)
    private BigDecimal cupoAcumulado;

    @Column(name = "COSTO_TARJETA", precision = 10, scale = 2)
    private BigDecimal costoTarjeta;

    @Column(name = "COSTO_ESTADO_CUENTA", precision = 10, scale = 2)
    private BigDecimal costoEstadoCuenta;

    @Column(name = "FECHA_CADUCIDAD_PLASTICO")
    private LocalDate fechaCaducidadPlastico;

    @Column(name = "FECHA_RETIRO")
    private LocalDate fechaRetiro;

    @Column(name = "FECHA_RENOVACION")
    private LocalDate fechaRenovacion;

    @Column(name = "FECHA_PERDIDA")
    private LocalDate fechaPerdida;

    @Column(name = "FECHA_CADUCIDAD")
    private LocalDate fechaCaducidad;

    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso;

    @Column(name = "FECHA_CORTE")
    private LocalDate fechaCorte;

    @Column(name = "FECHA_CANCELACION")
    private LocalDate fechaCancelacion;

    @Column(name = "FECHA_EMISION")
    private LocalDate fechaEmision;

    @Column(name = "FECHA_PROXIMO_CORTE")
    private LocalDate fechaProximoCorte;

    @Column(name = "FECHA_PAGO")
    private LocalDate fechaPago;

    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDate fechaVencimiento;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @Column(name = "FECHA_ENVIO")
    private LocalDate fechaEnvio;

    @Column(name = "FECHA_ANULACION")
    private LocalDate fechaAnulacion;

    @Column(name = "FECHA_DUPLICADO")
    private LocalDate fechaDuplicado;

    @Column(name = "FECHA_REACTIVACION")
    private LocalDate fechaReactivacion;

    @Column(name = "CAMPO_FECHA1")
    private LocalDate campoFecha1;

    @Column(name = "CAMPO_FECHA2")
    private LocalDate campoFecha2;

    @Column(name = "NUMERO_DIAS")
    private Integer numeroDias;

    @Column(name = "DUPLICADOS")
    private Integer duplicados;

    @Column(name = "RENOVACIONES")
    private Integer renovaciones;

    @Column(name = "CORTE")
    private Integer corte;

    @Column(name = "CAMPO_NUM1")
    private Long campoNum1;

    @Column(name = "CAMPO_NUM2")
    private Long campoNum2;


    @Column(name = "ESTADO_CUENTA", length = 50)
    private String estadoCuenta;

    @Column(name = "TIPO_CLIENTE", length = 50)
    private String tipoCliente;



    @Column(name = "ESTADO_TARJETA", length = 50)
    private String estadoTarjeta;

    @Column(name = "TARJETA_PRINCIPAL", length = 1)
    private String tarjetaPrincipal;

    @Column(name = "RADICACION_ADICIONAL", length = 20)
    private String radicacionAdicional;

    @Column(name = "NUMERO_RADICACION", length = 20)
    private String numeroRadicacion;

    @Column(name = "TARJETA_PERDIDA", length = 1)
    private String tarjetaPerdida;

    @Column(name = "PRODUCTO_VITALCARD", length = 50)
    private String productoVitalcard;

    @Column(name = "CODIGO_PERSONA", length = 20)
    private String codigoPersona;

    @Column(name = "CAUSAL_CANCELACION", length = 10)
    private String causalCancelacion;

    @Column(name = "DEBITO_AUTOMATICO", length = 1)
    private String debitoAutomatico;

    @Column(name = "NUMERO_CUENTA", length = 20)
    private String numeroCuenta;

    @Column(name = "TIPO_CUENTA", length = 10)
    private String tipoCuenta;

    @Column(name = "BANCO", length = 10)
    private String banco;

    @Column(name = "CONVENIO", length = 20)
    private String convenio;

    @Column(name = "CAUSAL_ANULACION", length = 10)
    private String causalAnulacion;

    @Column(name = "CAUSAL_MODIFICACION_CUPO", length = 10)
    private String causalModificacionCupo;

    @Column(name = "CAUSAL_REACTIVACION", length = 10)
    private String causalReactivacion;

    @Column(name = "CENTRO_COSTOS_VITALCARD", length = 20)
    private String centroCostosVitalcard;

    @Column(name = "TARJETA_ORIGEN", length = 20)
    private String tarjetaOrigen;

    @Column(name = "TARJETA_CANJEADA", length = 20)
    private String tarjetaCanjeada;

    @Column(name = "DOCUMENTO", length = 20)
    private String documento;

    @Column(name = "CONTRATO", length = 20)
    private String contrato;

    @Column(name = "CODIGO_EMPLEADO_EMPRESA", length = 20)
    private String codigoEmpleadoEmpresa;

    @Column(name = "ACTIVO_FACTURACUENTA", length = 1)
    private String activoFacturaCuenta;

    @Column(name = "TRA_CORTE", length = 20)
    private String traCorte;

    @Column(name = "FIRMA", length = 1)
    private String firma;

    @Column(name = "CALIFICACION_CLIENTE", length = 10)
    private String calificacionCliente;

    @Column(name = "IMPRIME_ESTCTA", length = 1)
    private String imprimeEstCta;

    @Column(name = "CALIFICACION_TARJETA", length = 10)
    private String calificacionTarjeta;

    @Column(name = "CAMPO_VARC1", length = 100)
    private String campoVarc1;

    @Column(name = "CAMPO_VARC2", length = 100)
    private String campoVarc2;


}
