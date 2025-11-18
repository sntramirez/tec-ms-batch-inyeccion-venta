package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.client;

import ec.femsasalud.com.sales.injection.batch.domain.service.UserLoginService;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserRequest;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.external.dto.CoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserLoginClient implements UserLoginService {

    private final WebClientService webClientService;

    @Override
    public Mono<CoUserResponse> login(CoUserRequest coUserRequest, String url) {
        return webClientService.post(url, coUserRequest, CoUserResponse.class);
    }
}
