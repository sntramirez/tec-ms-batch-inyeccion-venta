package ec.femsasalud.com.sales.injection.batch.application.usecase;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.ApiResponseDTO;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;
import ec.femsasalud.com.sales.injection.batch.domain.repository.LogFacturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FactureUseCase {

    private final LogFacturaRepository logFacturaRepository;

    public ApiResponseDTO<List<LogOrdenErrorDTO>> processFacture(VOFactura facture) {
        try {
            log.info("Procesando factura con orderId: {}", facture.getOrderId());
            
            // Obtener las órdenes con error desde el repositorio
            List<LogOrdenErrorDTO> facturas = logFacturaRepository.obtenerOrdenesError();
            
            // Validar resultado
            if (facturas == null || facturas.isEmpty()) {
                log.info("No se encontraron órdenes con error");
                return new ApiResponseDTO<>(
                    204, // No Content
                    "No se encontraron órdenes con error",
                    null,
                    null,
                    LocalDateTime.now()
                );
            }
            
            log.info("Se encontraron {} órdenes con error", facturas.size());
            
            // Mapear a un ApiResponseDTO exitoso
            return new ApiResponseDTO<>(
                200, // OK
                "Órdenes con error recuperadas exitosamente",
                facturas,
                null,
                LocalDateTime.now()
            );
            
        } catch (Exception e) {
            // Registrar el error
            log.error("Error al procesar factura: {}", e.getMessage(), e);
            
            // Devolver respuesta de error
            return new ApiResponseDTO<>(
                500, // Internal Server Error
                "Error al procesar la factura",
                null,
                e.getMessage(),
                LocalDateTime.now()
            );
        }
    }
}
