package ec.femsasalud.com.sales.injection.batch.domain.service;

import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserRequest;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserResponse;
import reactor.core.publisher.Mono;

public interface UserLoginService {
    Mono<CoUserResponse> login(CoUserRequest user , String url);
}
