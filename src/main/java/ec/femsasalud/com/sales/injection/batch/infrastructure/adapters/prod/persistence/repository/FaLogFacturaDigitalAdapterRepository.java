package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.LogFactura;
import ec.femsasalud.com.sales.injection.batch.domain.repository.LogFacturaRepository;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper.FaLogFacturaDigitalMapper;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaLogFacturaDigitalAdapterRepository implements LogFacturaRepository {

    private final FaLogFacturaDigitalJpaRepository tbFacturasJpaRepository;
    private final FaLogFacturaDigitalMapper faLogFacturaDigitalMapper;

    @Override
    public void insertLogFactura(LogFactura logFactura) {
        tbFacturasJpaRepository.save(faLogFacturaDigitalMapper.toEntity(logFactura));
    }

    @Override
    public List<LogOrdenErrorDTO> obtenerOrdenesError() {
        // Llamar al método con parámetros nulos para obtener todos los registros
        return obtenerOrdenesError(null, null);
    }

    public List<LogOrdenErrorDTO> obtenerOrdenesError(Date fechaDesde, Date fechaHasta) {
        log.info("Buscando órdenes con error - Fecha desde: {}, Fecha hasta: {}", 
                 fechaDesde != null ? fechaDesde : "Sin límite", 
                 fechaHasta != null ? fechaHasta : "Sin límite");
        
        List<Tuple> tuples = tbFacturasJpaRepository.obtenerOrdenesConErrorPorDefecto(fechaDesde, fechaHasta);
        return tuples.stream()
            .map(faLogFacturaDigitalMapper::mapTupleToLogOrdenErrorDTO)
            .collect(Collectors.toList());
    }
}
