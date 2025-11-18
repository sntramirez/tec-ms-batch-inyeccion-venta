package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Service
public class WebClientService {

    private final WebClient webClient;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
    }

    // Método principal para POST con body
    public <T, R> Mono<R> post(String url, T request, Class<R> responseType) {
        return post(url, request, null, responseType);
    }

    // POST con headers personalizados
    public <T, R> Mono<R> post(String url, T request, Map<String, String> headers, Class<R> responseType) {
        return executeRequest(HttpMethod.POST, url, request, headers, responseType);
    }

    // GET simple
    public <R> Mono<R> get(String url, Class<R> responseType) {
        return get(url, null, responseType);
    }

    // GET con headers
    public <R> Mono<R> get(String url, Map<String, String> headers, Class<R> responseType) {
        return executeRequest(HttpMethod.GET, url, null, headers, responseType);
    }

    // PUT con body
    public <T, R> Mono<R> put(String url, T request, Class<R> responseType) {
        return put(url, request, null, responseType);
    }

    // PUT con headers
    public <T, R> Mono<R> put(String url, T request, Map<String, String> headers, Class<R> responseType) {
        return executeRequest(HttpMethod.PUT, url, request, headers, responseType);
    }

    // DELETE
    public <R> Mono<R> delete(String url, Class<R> responseType) {
        return delete(url, null, responseType);
    }

    // DELETE con headers
    public <R> Mono<R> delete(String url, Map<String, String> headers, Class<R> responseType) {
        return executeRequest(HttpMethod.DELETE, url, null, headers, responseType);
    }

    // Método principal que maneja todas las operaciones
    private <T, R> Mono<R> executeRequest(HttpMethod method, String url, T request,
                                          Map<String, String> headers, Class<R> responseType) {

        WebClient.RequestBodySpec requestSpec = webClient
                .method(method)
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // Agregar headers si existen
        if (headers != null && !headers.isEmpty()) {
            requestSpec = requestSpec.headers(httpHeaders ->
                    headers.forEach(httpHeaders::add));
        }

        // Agregar body si existe (para POST, PUT, PATCH)
        WebClient.RequestHeadersSpec<?> headersSpec = request != null
                ? requestSpec.bodyValue(request)
                : requestSpec;

        return headersSpec
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofSeconds(60))
                .onErrorResume(this::handleError);
    }

    // Método con retry personalizado
    public <T, R> Mono<R> postWithRetry(String url, T request, Class<R> responseType,
                                        int maxRetries, Duration retryDelay) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(Retry.fixedDelay(maxRetries, retryDelay)
                        .filter(this::isRetryableError))
                .onErrorResume(this::handleError);
    }

    // Método para requests con autenticación Bearer
    public <T, R> Mono<R> postWithBearerAuth(String url, T request, String token, Class<R> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(this::handleError);
    }

    // Método para requests con autenticación Basic
    public <T, R> Mono<R> postWithBasicAuth(String url, T request, String username,
                                            String password, Class<R> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth(username, password))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(this::handleError);
    }

    // Manejo de errores mejorado
    private <T> Mono<T> handleError(Throwable throwable) {
        if (throwable instanceof WebClientResponseException webClientException) {
            int statusCode = webClientException.getStatusCode().value();
            String responseBody = webClientException.getResponseBodyAsString();

            return Mono.error(new WebClientException(
                    String.format("HTTP %d: %s - Response: %s",
                            statusCode, webClientException.getMessage(), responseBody),
                    throwable
            ));
        }

        return Mono.error(new WebClientException(
                "Error en la comunicación con el servicio externo: " + throwable.getMessage(),
                throwable
        ));
    }

    // Verificar si el error es reintentable
    private boolean isRetryableError(Throwable throwable) {
        if (throwable instanceof WebClientResponseException webClientException) {
            int statusCode = webClientException.getStatusCode().value();
            // Reintentar solo en errores del servidor (5xx) o timeouts
            return statusCode >= 500 || statusCode == 408;
        }
        return false;
    }

    // Excepción personalizada
    public static class WebClientException extends RuntimeException {
        public WebClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
