package org.salamansar.oder;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Salamansar
 */
@Configuration
@EnableJpaRepositories(value = "org.salamansar.oder.core.dao", 
        entityManagerFactoryRef = "emFactory")
@EnableTransactionManagement
public class RootAppConfig {
    
    @Bean
    public DataSource embeddedDB() {
        return new EmbeddedDatabaseBuilder()
                .build();
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean emFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(embeddedDB());
        factory.setJpaVendorAdapter(adapter);
        factory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
        factory.setPackagesToScan("org.salamansar.oder.core.domain");        
        return factory;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(emf);
        return manager;
    }
    
    
}
