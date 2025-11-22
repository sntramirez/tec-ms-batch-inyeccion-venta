package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.web.controller;

import ec.femsasalud.com.sales.injection.batch.infrastructure.scheduler.SriDigitalInvoiceScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/sri/digital-invoice")
@RequiredArgsConstructor
public class SriDigitalInvoiceController {

    private final SriDigitalInvoiceScheduler scheduler;

    /**
     * Endpoint para ejecutar manualmente el proceso de consulta al SRI
     * y procesamiento de facturas digitales pendientes.
     *
     * Usa el mismo mecanismo del scheduler para evitar ejecuciones concurrentes.
     * Si ya hay un procesamiento en curso, se rechazará la solicitud.
     */
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processDigitalInvoices() {
        log.info("Solicitud de procesamiento manual de facturas digitales recibida");

        Map<String, Object> response = new HashMap<>();

        try {
            // Ejecutar usando el scheduler que tiene control de concurrencia
            scheduler.runSriDigitalInvoiceJob();

            response.put("status", "success");
            response.put("message", "Procesamiento de facturas digitales completado exitosamente");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error al procesar facturas digitales manualmente", e);

            response.put("status", "error");
            response.put("message", "Error al procesar facturas digitales: " + e.getMessage());
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "SRI Digital Invoice Service");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}
