package ec.femsasalud.com.sales.injection.batch.domain.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaParametrosFacturador;

import java.util.List;

public interface FaParametrosFacturadorRepository {
    FaParametrosFacturador obtenerParametrosFacturador(String clave);
    List<FaParametrosFacturador> obtenerParametrosFacturador(List<String> claves);
}
