package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.envbuild.generator.RandomGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class IncomeServiceImplIT extends AbstractCoreIntegrationTest {
    @Autowired
	private EntityManager entityManager;
	@Autowired
	private IncomeService incomeService;
	
	private RandomGenerator generator = new RandomGenerator();
	private User user;
    
    @Before
    public void setUp() {
		user = generator.generate(User.class);
		user.setId(null);
		entityManager.persist(user);
    }

    @Test
    public void addIncome() {
		Income income = new Income();
		income.setAmount(BigDecimal.valueOf(10000));
		income.setDescription("Test description");
		income.setDocumentNumber(658);
		income.setIncomeDate(new Date());
		income.setUser(user);
	
		Long newId = incomeService.addIncome(income);
		
		assertNotNull(newId);
    }
	
    @Test
    public void getAllIncomes() {
		Income income1 = generator.generate(Income.class, user);
		income1.setId(null);
		entityManager.persist(income1);
		Income income2 = generator.generate(Income.class, user);
		income2.setId(null);
		entityManager.persist(income2);
	
		List<Income> incomes = incomeService.getAllIncomes(user);
		
		assertNotNull(incomes);
		assertEquals(2, incomes.size());
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income1.getId())));
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income2.getId())));
	}
    
}
