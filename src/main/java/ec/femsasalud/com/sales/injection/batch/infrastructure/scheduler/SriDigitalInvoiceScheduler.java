package ec.femsasalud.com.sales.injection.batch.infrastructure.scheduler;

import ec.femsasalud.com.sales.injection.batch.application.usecase.ProcessDigitalInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SriDigitalInvoiceScheduler {

    private final ProcessDigitalInvoiceUseCase processDigitalInvoiceUseCase;

    /**
     * Ejecuta el procesamiento de facturas digitales cada 30 minutos
     * Para cambiar la frecuencia, modifica el cron expression
     */
    @Scheduled(cron = "0 */30 * * * *")
    public void runSriDigitalInvoiceJob() {
        try {
            log.info("Iniciando ejecución programada del procesamiento de facturas digitales SRI");

            processDigitalInvoiceUseCase.processPendingInvoices();

            log.info("Procesamiento de facturas digitales SRI ejecutado exitosamente");

        } catch (Exception e) {
            log.error("Error al ejecutar el procesamiento programado de facturas digitales SRI", e);
        }
    }
}
