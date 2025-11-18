package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tb_factura",schema="ginvoice")
public class TbFactura implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema = "ginvoice",name = "seq_factura", sequenceName = "seq_factura", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_factura")
	private BigDecimal id;

	private String agencia;

	private String archivo;

	@Column(name="ARCHIVO_GEN_REIM")
	private String archivoGenReim;

	@Column(name="ARCHIVO_LEGIBLE")
	private String archivoLegible;

	@Column(name="BASE_CERO")
	private BigDecimal baseCero;

	@Column(name="BASE_DOCE")
	private BigDecimal baseDoce;

	@Column(name="BASE_ICE")
	private BigDecimal baseIce;

	@Column(name="BASE_IRBP")
	private BigDecimal baseIrbp;

	@Column(name="CLAVE_ACCESO")
	private String claveAcceso;

	@Column(name="CLAVE_INTERNA")
	private String claveInterna;

	@Column(name="COD_DOC")
	private String codDoc;

	@Column(name="COD_PUNTO_EMISION")
	private String codPuntoEmision;

	@Column(name="COD_SECUENCIAL")
	private String codSecuencial;

	@Column(name="CONTRIBUYENTE_ESPECIAL")
	private String contribuyenteEspecial;

	@Column(name="CORREO_NOTIFICACION")
	private String correoNotificacion;

	@Column(name="DIR_ESTABLECIMIENTO")
	private String dirEstablecimiento;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_AUTORIZACION")
	private Date fechaAutorizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_EMISION_BASE")
	private Date fechaEmisionBase;

	@Column(name="FECHA_EMISION_TMP")
	private String fechaEmisionTmp;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ENT_REIM")
	private Date fechaEntReim;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LEC_TRAD")
	private Date fechaLecTrad;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RECEP_MAIL")
	private Date fechaRecepMail;

	@Column(name="GUIA_REMISION")
	private String guiaRemision;

	private BigDecimal ice;

	@Column(name="ID_LOTE")
	private BigDecimal idLote;

	@Column(name="IDENTIFICACION_COMPRADOR")
	private String identificacionComprador;

	@Column(name="IDENTIFICADOR_USUARIO")
	private String identificadorUsuario;

	@Column(name="IMPORTE_TOTAL")
	private BigDecimal importeTotal;

	@Column(name="INFO_ADICIONAL")
	private String infoAdicional;

	private BigDecimal irbp;

	@Column(name="IS_OFERTON")
	private String isOferton;

	private BigDecimal iva;

	@Column(name="MENSAJE_REIM")
	private String mensajeReim;

	@Column(name="MODO_ENVIO")
	private String modoEnvio;

	private String moneda;

	@Column(name="NUMERO_AUTORIZACION")
	private String numeroAutorizacion;

	@Column(name="OBLIGADO_CONTABILIDAD")
	private String obligadoContabilidad;

	@Column(name="OBSERVACION_CANCELACION")
	private String observacionCancelacion;

	@Column(name="ORDEN_COMPRA")
	private String ordenCompra;

	private String proceso;

	private BigDecimal propina;

	@Column(name="PTO_EMISION")
	private String ptoEmision;

	@Column(name="RAZON_SOCIAL_COMPRADOR")
	private String razonSocialComprador;

	@Column(name="REQUIERE_CANCELACION")
	private String requiereCancelacion;

	private String ruc;

	@Column(name="SEC_NOTA_CREDITO")
	private String secNotaCredito;

	@Column(name="SEC_ORIGINAL")
	private String secOriginal;

	private String tarea;

	@Column(name="TIPO_AMBIENTE")
	private String tipoAmbiente;

	@Column(name="TIPO_EJECUCION")
	private String tipoEjecucion;

	@Column(name="TIPO_EMISION")
	private String tipoEmision;

	@Column(name="TIPO_GENERACION")
	private String tipoGeneracion;

	@Column(name="TIPO_ID_COMPRADOR")
	private String tipoIdComprador;

	@Column(name="TOTAL_DESCUENTO")
	private BigDecimal totalDescuento;

	@Column(name="TOTAL_SIN_IMPUESTOS")
	private BigDecimal totalSinImpuestos;

}
