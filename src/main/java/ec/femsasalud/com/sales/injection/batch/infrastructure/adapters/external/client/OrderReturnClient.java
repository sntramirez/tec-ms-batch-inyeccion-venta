package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.domain.service.OrderReturnService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoNotaCredito;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class  OrderReturnClient implements OrderReturnService {

    private final WebClientService webClientService;

    @Override
    public Mono<VOResponse> manageReturnOrder(CoNotaCredito coFactura, CoHeader header, String url) {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer "+header.authorization(),
                "Content-Type", header.contentType()
        );
        return webClientService.post(url, coFactura, headers, CoOrderResponse.class)
                .map(
                        coOrderResponse -> new VOResponse(coOrderResponse.getCode(), coOrderResponse.getMessage(), null)
                );
    }
}
