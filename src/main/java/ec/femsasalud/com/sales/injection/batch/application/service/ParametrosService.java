package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaParametrosFacturador;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FaParametrosFacturadorRepository;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParametrosService {

    private final FaParametrosFacturadorRepository repository;
    private final Map<String, String> parametrosCache = new ConcurrentHashMap<>();
    private volatile boolean cacheInitialized = false;

    /**
     * Carga todos los parámetros al iniciar la aplicación.
     * Los parámetros se mantienen en memoria durante toda la vida del pod.
     * Si se necesitan cambios, reiniciar el pod para recargar los parámetros.
     */
    @PostConstruct
    public void initializeCache() {
        loadAllParameters();
    }

    private void loadAllParameters() {
        try {
            List<String> allKeys = Arrays.stream(ParametroKey.values()).map(ParametroKey::getClave).toList();

            List<FaParametrosFacturador> parametros = repository.obtenerParametrosFacturador(allKeys);

            parametrosCache.clear();
            parametros.forEach(param -> parametrosCache.put(param.clave(), param.valor()));

            cacheInitialized = true;
            log.info("Cache de parámetros inicializado con {} elementos. Para aplicar cambios reinicie el pod.", parametros.size());

        } catch (Exception e) {
            log.error("Error al inicializar cache de parámetros", e);
            cacheInitialized = false;
        }
    }

    public String getParametro(ParametroKey key) {
        return getParametro(key.getClave());
    }

    public String getParametro(String clave) {
        if (!cacheInitialized) {
            loadAllParameters();
        }

        String valor = parametrosCache.get(clave);
        if (valor == null) {
            log.warn("Parámetro no encontrado en cache: {}", clave);
            // Fallback: buscar directamente en BD
            return getParametroFromDatabase(clave);
        }
        return valor;
    }

    public Optional<String> getParametroOptional(ParametroKey key) {
        return Optional.ofNullable(getParametro(key));
    }

    public String getParametroOrDefault(ParametroKey key, String defaultValue) {
        String valor = getParametro(key);
        return valor != null ? valor : defaultValue;
    }

    private String getParametroFromDatabase(String clave) {
        try {
            List<FaParametrosFacturador> parametros = repository.obtenerParametrosFacturador(List.of(clave));
            if (!parametros.isEmpty()) {
                String valor = parametros.get(0).valor();
                // Actualizar cache
                parametrosCache.put(clave, valor);
                return valor;
            }
        } catch (Exception e) {
            log.error("Error al obtener parámetro de BD: {}", clave, e);
        }
        return null;
    }

    /**
     * Limpia el cache de parámetros.
     * Útil para testing o casos especiales donde se necesite recargar manualmente.
     */
    public void clearCache() {
        parametrosCache.clear();
        cacheInitialized = false;
        log.info("Cache de parámetros limpiado");
    }
}
