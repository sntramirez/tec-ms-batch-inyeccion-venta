package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.ginvoice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author vjcardenast
 *
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_NOTA_CREDITO",schema="ginvoice")
public class TbNotaCredito implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="ginvoice",name = "seq_nota_credito", sequenceName = "seq_nota_credito", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_credito")
	@Column(name = "id", nullable = false)
	private Long id;	

	private String agencia;

	private String archivo;

	@Column(name="ARCHIVO_GEN_REIM")
	private String archivoGenReim;

	@Column(name="ARCHIVO_LEGIBLE")
	private String archivoLegible;

	@Column(name="CLAVE_ACCESO")
	private String claveAcceso;

	@Column(name="CLAVE_INTERNA")
	private String claveInterna;

	@Column(name="COD_DOC")
	private String codDoc;

	@Column(name="COD_DOC_MODIFICADO")
	private String codDocModificado;

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
	@Column(name="FECH_EMI_DOC_SUSTENTO")
	private Date fechEmiDocSustento;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_AUTORIZACION")
	private Date fechaAutorizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_EMISION")
	private Date fechaEmision;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ENT_REIM")
	private Date fechaEntReim;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LEC_TRAD")
	private Date fechaLecTrad;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RECEP_MAIL")
	private Date fechaRecepMail;

	@Column(name="ID_LOTE")
	private BigDecimal idLote;

	@Column(name="IDENTIFICACION_COMPRADOR")
	private String identificacionComprador;

	@Column(name="IDENTIFICADOR_USUARIO")
	private String identificadorUsuario;

	@Column(name="INFO_ADICIONAL")
	private String infoAdicional;

	@Column(name="IS_OFERTON")
	private String isOferton;

	@Column(name="MENSAJE_REIM")
	private String mensajeReim;

	@Column(name="MODO_ENVIO")
	private String modoEnvio;

	private String moneda;

	private String motivo;

	@Column(name="NUM_DOC_MODIFICADO")
	private String numDocModificado;

	@Column(name="NUMERO_AUTORIZACION")
	private String numeroAutorizacion;

	@Column(name="OBLIGADO_CONTABILIDAD")
	private String obligadoContabilidad;

	@Column(name="ORDEN_COMPRA")
	private String ordenCompra;

	private String proceso;

	@Column(name="PTO_EMISION")
	private String ptoEmision;

	@Column(name="RAZON_SOCIAL_COMPRADOR")
	private String razonSocialComprador;

	private String rise;

	private String ruc;

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

	@Column(name="TOTAL_SIN_IMPUESTOS")
	private BigDecimal totalSinImpuestos;

	@Column(name="VALOR_MODIFICACION")
	private BigDecimal valorModificacion;

}
