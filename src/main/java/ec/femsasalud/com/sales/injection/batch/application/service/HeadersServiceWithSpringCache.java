package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.domain.service.UserLoginService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserRequest;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserResponse;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeadersServiceWithSpringCache {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final Duration LOGIN_TIMEOUT = Duration.ofSeconds(30);

    private final FaColaFacturaDigitalService faColaFacturaDigitalService;
    private final UserLoginService userLoginService;
    private final HeadersConfigService headersConfigService;
    private final ParametrosService parametrosService;

    @Cacheable(value = "authTokens", key = "'default'", unless = "#result == null")
    public Mono<CoHeader> getHeaderAsync(VOFactura document, String jsonDocument) {
        return createLoginRequestFromConfig()
                .flatMap(loginRequest -> performLogin(loginRequest, document, jsonDocument))
                .map(response -> new CoHeader(CONTENT_TYPE_JSON, response.getToken()))
                .doOnError(error -> log.error("Error obteniendo header de autenticación", error));
    }

    public CoHeader getHeader(VOFactura document, String jsonDocument) {
        return getHeaderAsync(document, jsonDocument).block();
    }

    private Mono<CoUserRequest> createLoginRequestFromConfig() {
        return Mono.fromCallable(() -> {
            var credentials = headersConfigService.getLoginCredentials();
            return new CoUserRequest(credentials.username(), credentials.password());
        }).doOnError(error -> log.error("Error creando request de login desde configuración", error));
    }

    private Mono<CoUserResponse> performLogin(CoUserRequest loginRequest, VOFactura document, String jsonDocument) {
        return userLoginService.login(loginRequest, parametrosService.getParametro(ParametroKey.SERVICE_USER_LOGIN))
                .timeout(LOGIN_TIMEOUT)
                .doOnSuccess(response -> log.debug("Login completado exitosamente"))
                .onErrorResume(error -> {
                    log.error("Error durante el login", error);
                    return handleLoginError(error, document, jsonDocument)
                            .then(Mono.empty()); // Retorna empty para mantener la cadena reactiva
                });
    }

    private Mono<Void> handleLoginError(Throwable error, VOFactura document, String jsonDocument) {
        return Mono.fromRunnable(() -> {
            String errorMessage = buildErrorMessage(error);
            faColaFacturaDigitalService.saveFaColaFactura(document, jsonDocument, errorMessage);
            throw new RuntimeException("Error con el login:".concat(errorMessage));
        });
    }

    private String buildErrorMessage(Throwable error) {
        return "Error al procesar la orden de venta: " +
                Optional.ofNullable(error.getMessage()).orElse("Error desconocido");
    }

}
