package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoBonus;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoBonusResponse;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoHeader;
import reactor.core.publisher.Mono;

public interface CustomerBonusService {
    Mono<CoBonusResponse> accumulateCustomerBonus(CoBonus coBonus, CoHeader header, String url);
}
