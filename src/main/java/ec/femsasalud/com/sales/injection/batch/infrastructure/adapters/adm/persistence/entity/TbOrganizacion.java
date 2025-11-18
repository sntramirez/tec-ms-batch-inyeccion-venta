package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="tb_organizacion" , schema = "adm")
public class TbOrganizacion {

	@Id
	private long id;

	private String acronimo;

	@Column(name="ACRONIMO_OPCIONAL")
	private String acronimoOpcional;

	@Column(name="CEDIS_VIRTUAL")
	private String cedisVirtual;

	@Column(name="CLAVE_TOKEN")
	private String claveToken;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name="DIR_ESTABLECIMIENTO")
	private String dirEstablecimiento;

	private String direccion;

	@Column(name="EN_CONTINGENCIA")
	private String enContingencia;

	@Column(name="ES_OBLIGADO_CONTABILIDAD")
	private String esObligadoContabilidad;

	private String establecimiento;

	private String estado;

	@Column(name="ID_CIUDAD")
	private java.math.BigDecimal idCiudad;

	@Column(name="LOGO_EMPRESA")
	private String logoEmpresa;

	@Column(name="LOGO_EMPRESA_OPCIONAL")
	private String logoEmpresaOpcional;

	private String nombre;

	@Column(name="NOMBRE_COMERCIAL")
	private String nombreComercial;

	private String puntoemision;

	@Column(name="RESOL_CONTRIBUYENTE_ESP")
	private String resolContribuyenteEsp;

	private String ruc;

	private String telefono;

	private String token;

	@ManyToOne
	@JoinColumn(name="ID_ORGANIZACION_PADRE")
	private TbOrganizacion tbOrganizacion;

	@OneToMany(mappedBy="tbOrganizacion")
	private List<TbOrganizacion> tbOrganizacions;
}
