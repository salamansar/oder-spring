package org.salamansar.oder;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 *
 * @author Salamansar
 */
public class FlywayMigrator  {
	@Autowired
	private DataSource dataSource;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("flyway", "local")
				.load();
		flyway.migrate();
	}
	
}
