package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoNotaCredito;
import reactor.core.publisher.Mono;

public interface OrderReturnService {
    Mono<VOResponse> manageReturnOrder(CoNotaCredito coFactura, CoHeader header, String url);
}
