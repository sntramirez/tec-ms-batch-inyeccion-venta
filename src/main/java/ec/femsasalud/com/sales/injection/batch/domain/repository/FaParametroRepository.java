package ec.femsasalud.com.sales.injection.batch.domain.repository;

import java.sql.Connection;

public interface FaParametroRepository {

    String obtenerParametro(String clave);

    String obtenerFarmacia(Connection conn, Long codigo);

    String obtenerCodigoMetodoPagoFarmacia(Connection conn, String codigoPago, String codigoMetodoPago);
}
