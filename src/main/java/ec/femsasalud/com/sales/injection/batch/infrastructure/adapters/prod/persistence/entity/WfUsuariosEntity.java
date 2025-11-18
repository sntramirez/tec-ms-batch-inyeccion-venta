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
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WF_USUARIOS")
public class WfUsuariosEntity {

    @Id
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    
    @Column(name = "CODIGO_PERSONA")
    private BigDecimal codigoPersona;
    
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "ACTIVO")
    private Boolean activo;
    
    @Column(name = "EMAIL")
    private String email;
}
