package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AB_PERSONAS")
public class AbPersonasEntity {

    @Id
    @Column(name = "CODIGO", nullable = false)
    private BigDecimal codigo;

    @Column(name = "TIPO_PERSONA", nullable = false, length = 3)
    private String tipoPersona;

    @Column(name = "IDENTIFICACION", length = 13)
    private String identificacion;

    @Column(name = "TIPO_IDENTIFICACION", nullable = false, length = 1)
    private String tipoIdentificacion;

    @Column(name = "RAZON_SOCIAL", length = 80, unique = true)
    private String razonSocial;

    @Column(name = "PRIMER_NOMBRE", length = 80)
    private String primerNombre;

    @Column(name = "SEGUNDO_NOMBRE", length = 80)
    private String segundoNombre;

    @Column(name = "PRIMER_APELLIDO", length = 80)
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO", length = 80)
    private String segundoApellido;

    @Column(name = "REPRESENTANTE_LEGAL", length = 80)
    private String representanteLegal;

    @Column(name = "IDENTIFICACION_REP_LEGAL", length = 13)
    private String identificacionRepLegal;

    @Column(name = "TIPO_IDENTIFICACION_REP_LEGAL", length = 1)
    private String tipoIdentificacionRepLegal;

    @Column(name = "TIPO_EMPRESA", length = 1)
    private String tipoEmpresa;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CONSTITUCION")
    private Date fechaConstitucion;

    @Column(name = "NOMBRE_COMERCIAL", length = 80)
    private String nombreComercial;

    @Column(name = "NUMERO_CARGAS_FAMILIARES")
    private Integer numeroCargarFamiliares;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "ESTADO_CIVIL", length = 1)
    private String estadoCivil;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    @Column(name = "PROFESION")
    private BigDecimal profesion;

    @Column(name = "NACIONALIDAD")
    private BigDecimal nacionalidad;

    @Column(name = "GRUPO_EMPRESARIAL")
    private BigDecimal grupoEmpresarial;

    @Column(name = "TERCERO", length = 10)
    private String tercero;

    @Column(name = "CIUDAD_NACIMIENTO")
    private BigDecimal ciudadNacimiento;

    @Column(name = "USUARIO_MODIFICA", length = 20)
    private String usuarioModifica;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;

    @Column(name = "TIPO_SANGRE")
    private BigDecimal tipoSangre;

    @Column(name = "TIPO_CLIENTE")
    private BigDecimal tipoCliente;

    @Column(name = "USUARIO_CREA", length = 20)
    private String usuarioCrea;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "PSA_FECHA_CREACION")
    private Date psaFechaCreacion;

    @Column(name = "PSA_USUARIO_CREA", length = 20)
    private String psaUsuarioCrea;
}
