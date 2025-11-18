package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper;

import ec.femsasalud.com.sales.injection.batch.domain.model.VcTarjetasDto;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.entity.VcTarjetasEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class VcTarjetasMapper {

    public VcTarjetasDto toDto(VcTarjetasEntity entity) {
        if (entity == null) {
            return null;
        }

        return new VcTarjetasDto(
                entity.getSaldoCupo(),
                entity.getPorcentajeSobregiro(),
                entity.getPagos(),
                entity.getSaldoConsumos(),
                entity.getCupo(),
                entity.getSaldoFavor(),
                entity.getDebitos(),
                entity.getSaldoTarjeta(),
                entity.getMoraAcumulada(),
                entity.getInteresMora(),
                entity.getCreditos(),
                entity.getSaldoCuenta(),
                entity.getCupoFarmacia(),
                entity.getCupoRotativo(),
                entity.getCupoAcumulado(),
                entity.getCostoTarjeta(),
                entity.getCostoEstadoCuenta(),
                entity.getFechaCaducidadPlastico(),
                entity.getFechaRetiro(),
                entity.getFechaRenovacion(),
                entity.getFechaPerdida(),
                entity.getFechaCaducidad(),
                entity.getFechaIngreso(),
                entity.getFechaCorte(),
                entity.getFechaCancelacion(),
                entity.getFechaEmision(),
                entity.getFechaProximoCorte(),
                entity.getFechaPago(),
                entity.getFechaVencimiento(),
                entity.getFechaActualizacion(),
                entity.getFechaEnvio(),
                entity.getFechaAnulacion(),
                entity.getFechaDuplicado(),
                entity.getFechaReactivacion(),
                entity.getCampoFecha1(),
                entity.getCampoFecha2(),
                entity.getNumeroDias(),
                entity.getDuplicados(),
                entity.getRenovaciones(),
                entity.getCorte(),
                entity.getCampoNum1(),
                entity.getCampoNum2(),
                entity.getEstadoCuenta(),
                entity.getTipoCliente(),
                entity.getNumeroTarjeta(),
                entity.getEstadoTarjeta(),
                entity.getTarjetaPrincipal(),
                entity.getRadicacionAdicional(),
                entity.getNumeroRadicacion(),
                entity.getTarjetaPerdida(),
                entity.getProductoVitalcard(),
                entity.getCodigoPersona(),
                entity.getCausalCancelacion(),
                entity.getDebitoAutomatico(),
                entity.getNumeroCuenta(),
                entity.getTipoCuenta(),
                entity.getBanco(),
                entity.getConvenio(),
                entity.getCausalAnulacion(),
                entity.getCausalModificacionCupo(),
                entity.getCausalReactivacion(),
                entity.getCentroCostosVitalcard(),
                entity.getTarjetaOrigen(),
                entity.getTarjetaCanjeada(),
                entity.getDocumento(),
                entity.getContrato(),
                entity.getCodigoEmpleadoEmpresa(),
                entity.getActivoFacturaCuenta(),
                entity.getTraCorte(),
                entity.getFirma(),
                entity.getCalificacionCliente(),
                entity.getImprimeEstCta(),
                entity.getCalificacionTarjeta(),
                entity.getCampoVarc1(),
                entity.getCampoVarc2(),
                calculateSaldoDisponible(entity),
                calculateActiva(entity),
                calculatePrincipal(entity),
                calculateTieneDebitoAutomatico(entity)
        );
    }
    
    private BigDecimal calculateSaldoDisponible(VcTarjetasEntity entity) {
        // Ejemplo simple, puede ser una regla de negocio específica
        return entity.getCupo() != null && entity.getSaldoConsumos() != null
                ? entity.getCupo().subtract(entity.getSaldoConsumos())
                : BigDecimal.ZERO;
    }
    
    private Boolean calculateActiva(VcTarjetasEntity entity) {
        // Ejemplo: una tarjeta está activa si estadoTarjeta es distinto de "INACTIVA"
        return entity.getEstadoTarjeta() != null && !entity.getEstadoTarjeta().equalsIgnoreCase("INACTIVA");
    }
    
    private Boolean calculatePrincipal(VcTarjetasEntity entity) {
        // Ejemplo: tarjeta principal si tarjetaPrincipal es "S" o "Y"
        String principal = entity.getTarjetaPrincipal();
        return principal != null && (principal.equalsIgnoreCase("S") || principal.equalsIgnoreCase("Y"));
    }
    
    private Boolean calculateTieneDebitoAutomatico(VcTarjetasEntity entity) {
        // Ejemplo: tiene debito automatico si debitoAutomatico es "S" o "Y"
        String debito = entity.getDebitoAutomatico();
        return debito != null && (debito.equalsIgnoreCase("S") || debito.equalsIgnoreCase("Y"));
    }
}
