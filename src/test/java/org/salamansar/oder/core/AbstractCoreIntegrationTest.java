package org.salamansar.oder.core;

import javax.persistence.EntityManager;
import org.envbuild.generator.processor.DomainPersister;
import org.envbuild.spring.config.PersistOrientedGeneratorConfig;
import org.junit.runner.RunWith;
import org.salamansar.oder.DatasourceConfig;
import org.salamansar.oder.RootAppConfig;
import org.salamansar.oder.core.domain.HasId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author Salamansar
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("local")
public abstract class AbstractCoreIntegrationTest {
	
	@Configuration
	@Import({RootAppConfig.class, PersistOrientedGeneratorConfig.class, DatasourceConfig.class})
	static class ContextConfiguration {
		
		@Bean
		public DomainPersister domainPersister(EntityManager entityManager) {
			return (domain) -> {
				if(domain instanceof HasId) {
					((HasId)domain).setId(null);
				}
				entityManager.persist(domain);
			};
		}
		
	}
	
	@Autowired
	protected EntityManager entityManager;
}
