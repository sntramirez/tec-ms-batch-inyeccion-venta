package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.LogFactura;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;

import java.util.Date;
import java.util.List;

public interface LogFacturaRepository {
    
    void insertLogFactura(LogFactura logFactura);
    
    List<LogOrdenErrorDTO> obtenerOrdenesError();

    List<LogOrdenErrorDTO> obtenerOrdenesError(Date fechaDesde, Date fechaHasta);
}
