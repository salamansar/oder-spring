package org.salamansar.oder;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author Salamansar
 */
@Configuration
@PropertySource("classpath:config.properties")
public class DatasourceConfig {
	
	@Bean
	@Profile("local")
	public DataSource embeddedDB() {
		return new EmbeddedDatabaseBuilder()
				.build();
	}
	
	@Bean
	@Profile("!local")
	public DataSource postgreDB(
			@Value("${database.url}") String url,
			@Value("${database.user}") String user,
			@Value("${database.password}") String password
	) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
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
