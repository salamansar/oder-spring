package org.salamansar.oder.module;

import org.salamansar.oder.module.WebAppConfig;
import javax.persistence.EntityManager;
import org.envbuild.generator.processor.DomainPersister;
import org.envbuild.spring.config.PersistOrientedGeneratorConfig;
import org.junit.runner.RunWith;
import org.salamansar.oder.RootAppConfig;
import org.salamansar.oder.core.domain.HasId;
import org.salamansar.oder.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Salamansar
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootAppConfig.class, PersistOrientedGeneratorConfig.class, SecurityConfig.class, WebAppConfig.class})
public abstract class AbstractWebAppIntegrationTest {
	
	@Configuration	
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
		
		@Bean
		public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
			return new TransactionTemplate(transactionManager);
		}
		
		
	}
	
	@Autowired
	protected EntityManager entityManager;
	@Autowired
	protected TransactionTemplate transactionTemplate;
}
