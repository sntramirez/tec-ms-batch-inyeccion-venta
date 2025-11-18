package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import reactor.core.publisher.Mono;

public interface PaymentConfirmationService {
    Mono<VOResponse> confirmPayment(Object paymentConfirmationRequest, String url); // El tipo Object se debe reemplazar por el DTO específico
}
