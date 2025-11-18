package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FA_IDENTIFICACION_CLIENTE", schema = "FARMACIAS")
public class FaIdentificacionClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String codigo;

    private String descripcion;
}

