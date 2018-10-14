package org.salamansar.oder.core;

import javax.persistence.EntityManager;
import org.junit.runner.RunWith;
import org.salamansar.oder.RootAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Salamansar
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootAppConfig.class)
public abstract class AbstractCoreIntegrationTest {
	@Autowired
	protected EntityManager entityManager;
}
