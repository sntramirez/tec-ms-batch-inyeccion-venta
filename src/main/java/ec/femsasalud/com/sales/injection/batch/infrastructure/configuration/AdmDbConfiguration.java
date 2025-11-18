/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.femsasalud.com.sales.injection.batch.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm",
    entityManagerFactoryRef = "admEntityManagerFactory",
    transactionManagerRef = "admTransactionManager"
)
public class AdmDbConfiguration {
    
    @Bean(name = "admDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.adm")
    public DataSource admDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "admEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean admEntityManagerFactory(
            @Qualifier("admDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.adm.persistence.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "admTransactionManager")
    public JpaTransactionManager admTransactionManager(
            @Qualifier("admEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
