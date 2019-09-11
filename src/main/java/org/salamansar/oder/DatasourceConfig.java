package org.salamansar.oder;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author Salamansar
 */
@Configuration
public class DatasourceConfig {
	
	@Bean
	@Profile("local")
	public DataSource embeddedDB() {
		return new EmbeddedDatabaseBuilder()
				.build();
	}
	
	@Bean
	@Profile("!local")
	public DataSource postgreDB() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost/oder");
		ds.setUsername("oder_user");
		ds.setPassword("100500");
		return ds;
	}
	

	@Bean
	public LocalContainerEntityManagerFactoryBean emFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(adapter);
		factory.setPackagesToScan("org.salamansar.oder.core.domain");
		return factory;
	}
	
	@Bean
	@Profile("local")
	public FlywayMigrator flywayMigrator() {
		return new FlywayMigrator();
	}

}
