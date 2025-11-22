package ec.femsasalud.com.sales.injection.batch.infrastructure.scheduler;

import ec.femsasalud.com.sales.injection.batch.application.service.ParametrosService;
import ec.femsasalud.com.sales.injection.batch.application.usecase.ProcessDigitalInvoiceUseCase;
import ec.femsasalud.com.sales.injection.batch.shared.common.ParametroKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SriDigitalInvoiceScheduler {

    private final ProcessDigitalInvoiceUseCase processDigitalInvoiceUseCase;
    private final ParametrosService parametrosService;
    private final TaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledTask;
    private static final String DEFAULT_CRON = "0 */30 * * * *"; // Cada 30 minutos por defecto

    /**
     * Inicializa el scheduler con el cron expression desde la base de datos
     * Lee el parámetro 'sri_scheduler_cron' de FA_PARAMETROS_FACTURADOR
     * Si no existe, usa el valor por defecto: cada 30 minutos
     */
    @PostConstruct
    public void initializeScheduler() {
        try {
            String cronExpression = parametrosService.getParametroOrDefault(
                ParametroKey.SRI_SCHEDULER_CRON,
                DEFAULT_CRON
            );

            log.info("Inicializando scheduler de facturas digitales SRI con cron: {}", cronExpression);

            scheduleTask(cronExpression);

        } catch (Exception e) {
            log.error("Error al inicializar scheduler, usando cron por defecto: {}", DEFAULT_CRON, e);
            scheduleTask(DEFAULT_CRON);
        }
    }

    /**
     * Programa la tarea con el cron expression especificado
     */
    private void scheduleTask(String cronExpression) {
        try {
            // Cancelar tarea anterior si existe
            if (scheduledTask != null) {
                scheduledTask.cancel(false);
            }

            // Programar nueva tarea
            scheduledTask = taskScheduler.schedule(
                this::runSriDigitalInvoiceJob,
                new CronTrigger(cronExpression)
            );

            log.info("Scheduler programado exitosamente con expresión: {}", cronExpression);

        } catch (Exception e) {
            log.error("Error al programar scheduler con cron: {}", cronExpression, e);
            throw new IllegalArgumentException("Cron expression inválido: " + cronExpression, e);
        }
    }

    /**
     * Ejecuta el procesamiento de facturas digitales
     * La frecuencia se configura desde la tabla FA_PARAMETROS_FACTURADOR
     * con la clave 'sri_scheduler_cron'
     */
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
