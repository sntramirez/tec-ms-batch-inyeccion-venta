package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.domain.service.OrderSaleService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoFactura;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderSaleClient implements OrderSaleService {

    private final WebClientService webClientService;

    @Override
    public Mono<VOResponse> manageSaleOrder(CoFactura coFactura, CoHeader header, String url) {
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
