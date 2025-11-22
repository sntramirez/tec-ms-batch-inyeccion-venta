package ec.femsasalud.com.sales.injection.batch.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfiguration {

    /**
     * Configura el TaskScheduler para ejecutar tareas programadas
     * Usado por SriDigitalInvoiceScheduler para programar dinámicamente
     * el procesamiento de facturas digitales
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("sri-scheduler-");
        scheduler.setAwaitTerminationSeconds(20);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.initialize();
        return scheduler;
    }
}
