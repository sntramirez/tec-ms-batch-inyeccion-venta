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

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SriDigitalInvoiceScheduler {

    private final ProcessDigitalInvoiceUseCase processDigitalInvoiceUseCase;
    private final ParametrosService parametrosService;
    private final TaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledTask;
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
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
     *
     * Incluye protección contra ejecuciones concurrentes:
     * Si el procesamiento anterior aún no termina, se salta la ejecución
     */
    public void runSriDigitalInvoiceJob() {
        // Verificar si ya hay un procesamiento en curso
        if (!isProcessing.compareAndSet(false, true)) {
            log.warn("=== PROCESAMIENTO SALTADO ===");
            log.warn("Ya existe una ejecución en curso del procesamiento de facturas digitales SRI");
            log.warn("Se omite esta ejecución programada para evitar sobrecarga");
            log.warn("Considere ajustar el cron expression en FA_PARAMETROS_FACTURADOR (sri_scheduler_cron)");
            return;
        }

        Instant startTime = Instant.now();

        try {
            log.info("=== INICIANDO PROCESAMIENTO DE FACTURAS DIGITALES SRI ===");
            log.info("Hora de inicio: {}", startTime);

            processDigitalInvoiceUseCase.processPendingInvoices();

            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);

            log.info("=== PROCESAMIENTO COMPLETADO EXITOSAMENTE ===");
            log.info("Tiempo total de ejecución: {} minutos {} segundos",
                duration.toMinutes(), duration.toSecondsPart());

        } catch (Exception e) {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);

            log.error("=== ERROR EN PROCESAMIENTO ===");
            log.error("Tiempo transcurrido antes del error: {} minutos {} segundos",
                duration.toMinutes(), duration.toSecondsPart());
            log.error("Error al ejecutar el procesamiento programado de facturas digitales SRI", e);

        } finally {
            // IMPORTANTE: Siempre liberar el flag, incluso si hay error
            isProcessing.set(false);
            log.info("Flag de procesamiento liberado. Listo para próxima ejecución.");
        }
    }
}
