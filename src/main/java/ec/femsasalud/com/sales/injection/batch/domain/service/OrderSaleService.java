package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoFactura;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import reactor.core.publisher.Mono;

public interface OrderSaleService {
    Mono<VOResponse> manageSaleOrder(CoFactura coFactura, CoHeader header, String url);
}
