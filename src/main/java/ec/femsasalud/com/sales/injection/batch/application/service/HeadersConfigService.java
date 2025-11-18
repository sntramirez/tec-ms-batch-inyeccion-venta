package ec.femsasalud.com.sales.injection.batch.application.service;

import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeadersConfigService {

    private final ParametrosService parametrosService;

    public LoginCredentials getLoginCredentials() {
        String username = parametrosService.getParametro(ParametroKey.SERVICE_USER_LOGIN_USERNAME);
        String password = parametrosService.getParametro(ParametroKey.SERVICE_USER_LOGIN_PASSWORD);

        if (username == null || password == null) {
            throw new IllegalStateException(
                    "Credenciales de login no configuradas: username=" + (username != null) +
                            ", password=" + (password != null)
            );
        }

        return new LoginCredentials(username, password);
    }

    public record LoginCredentials(String username, String password) {}
}
