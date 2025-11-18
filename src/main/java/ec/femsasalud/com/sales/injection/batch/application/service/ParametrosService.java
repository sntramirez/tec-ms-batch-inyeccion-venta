package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaParametrosFacturador;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FaParametrosFacturadorRepository;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
            log.info("Cache de parámetros inicializado con {} elementos", parametros.size());

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

    @Scheduled(fixedDelay = 300000) // 5 minutos
    public void refreshCache() {
        log.debug("Refrescando cache de parámetros");
        loadAllParameters();
    }

    public void clearCache() {
        parametrosCache.clear();
        cacheInitialized = false;
        log.info("Cache de parámetros limpiado");
    }
}
