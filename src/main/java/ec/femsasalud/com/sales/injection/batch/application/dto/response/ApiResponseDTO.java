package ec.femsasalud.com.sales.injection.batch.application.dto.response;

import java.time.LocalDateTime;

public record ApiResponseDTO<T>(
        int codigoRespuesta,
        String mensaje,
        T data,
        String error,
        LocalDateTime timestamp
) {}
