package org.salamansar.oder;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author Salamansar
 */
@Configuration
public class TestDatasourceConfig {
	
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

}
