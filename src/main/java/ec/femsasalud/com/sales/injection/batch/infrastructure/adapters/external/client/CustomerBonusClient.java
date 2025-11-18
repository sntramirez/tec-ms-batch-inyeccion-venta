package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import ec.femsasalud.com.sales.injection.batch.domain.service.CustomerBonusService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoBonus;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoBonusResponse;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerBonusClient implements CustomerBonusService {

    private final WebClientService webClientService;


    @Override
    public Mono<CoBonusResponse> accumulateCustomerBonus(CoBonus coBonus, CoHeader header, String url) {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer "+header.authorization(),
                "Content-Type", header.contentType()
        );
        return webClientService.post(url, coBonus, headers, CoBonusResponse.class)
                .map(
                        coOrderResponse -> new CoBonusResponse(coOrderResponse.voucher(), coOrderResponse.code(), coOrderResponse.description())
                );
    }
}
