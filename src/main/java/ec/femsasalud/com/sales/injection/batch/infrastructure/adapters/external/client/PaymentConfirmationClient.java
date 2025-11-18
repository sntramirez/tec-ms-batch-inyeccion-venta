package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.domain.service.PaymentConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class  PaymentConfirmationClient implements PaymentConfirmationService {

    private final WebClientService webClientService;

    @Override
    public Mono<VOResponse> confirmPayment(Object paymentConfirmationRequest, String url) {
        return null;
    }
}
