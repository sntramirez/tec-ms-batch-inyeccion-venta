package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_PARAMETRO")
public class TbParametro {

	@Id
	private long id;

	private String codigo;

	private String descripcion;

	private String encriptado;

	private String estado;

	@Column(name="ID_APLICACION")
	private BigDecimal idAplicacion;

	@Column(name="ID_ORGANIZACION")
	private Long idOrganizacion;

	@Column(name="TIPO_PARAMETRO")
	private String tipoParametro;

	private String valor;
}
