package ec.femsasalud.com.sales.injection.batch.infrastructure.configuration;

import ec.femsasalud.com.sales.injection.batch.application.service.SriDigitalInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SriDigitalInvoiceBatchConfig {

    private final SriDigitalInvoiceService sriDigitalInvoiceService;

    @Bean
    public Job sriDigitalInvoiceJob(JobRepository jobRepository, Step sriDigitalInvoiceStep) {
        return new JobBuilder("sriDigitalInvoiceJob", jobRepository)
                .start(sriDigitalInvoiceStep)
                .build();
    }

    @Bean
    public Step sriDigitalInvoiceStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sriDigitalInvoiceStep", jobRepository)
                .tasklet(sriDigitalInvoiceTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet sriDigitalInvoiceTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Ejecutando tasklet de procesamiento de facturas digitales SRI");
            try {
                sriDigitalInvoiceService.processPendingInvoices();
                log.info("Tasklet ejecutado exitosamente");
                return RepeatStatus.FINISHED;
            } catch (Exception e) {
                log.error("Error en la ejecución del tasklet", e);
                throw e;
            }
        };
    }
}
