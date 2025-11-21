package ec.femsasalud.com.sales.injection.batch.infrastructure.scheduler;

import ec.femsasalud.com.sales.injection.batch.application.usecase.ProcessDigitalInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SriDigitalInvoiceScheduler {

    private final JobLauncher jobLauncher;
    private final Job sriDigitalInvoiceJob;
    private final ProcessDigitalInvoiceUseCase processDigitalInvoiceUseCase;

    /**
     * Ejecuta el job cada 30 minutos
     * Para cambiar la frecuencia, modifica el cron expression
     */
    @Scheduled(cron = "0 */30 * * * *")
    public void runSriDigitalInvoiceJob() {
        try {
            log.info("Iniciando ejecución programada del job de facturas digitales SRI");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(sriDigitalInvoiceJob, jobParameters);

            log.info("Job de facturas digitales SRI ejecutado exitosamente");

        } catch (Exception e) {
            log.error("Error al ejecutar el job programado de facturas digitales SRI", e);
        }
    }
}
