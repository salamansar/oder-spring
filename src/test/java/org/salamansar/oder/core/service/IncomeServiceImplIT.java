package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.envbuild.generator.RandomGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
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
		income.setIncomeDate(LocalDate.now());
		income.setUser(user);

		Long newId = incomeService.addIncome(income);

		assertNotNull(newId);
	}

	@Test
	public void getAllIncomes() {
		Income income1 = generator.generate(Income.class, user, LocalDate.now());
		income1.setId(null);
		entityManager.persist(income1);
		Income income2 = generator.generate(Income.class, user, LocalDate.now());
		income2.setId(null);
		entityManager.persist(income2);

		List<Income> incomes = incomeService.getAllIncomes(user);

		assertNotNull(incomes);
		assertEquals(2, incomes.size());
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income1.getId())));
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income2.getId())));
		assertTrue(incomes.stream().allMatch(e -> e.getIncomeDate() != null));
	}

	@Test
	public void getIncomesForQuarter() {
		LocalDate date1 = LocalDate.of(2018, Month.MAY, 5);
		Income income1 = generator.generate(Income.class, user, date1);
		income1.setId(null);
		entityManager.persist(income1);
		LocalDate date2 = LocalDate.of(2018, Month.JULY, 1);
		Income income2 = generator.generate(Income.class, user, date2);
		income2.setId(null);
		entityManager.persist(income2);
		LocalDate date3 = LocalDate.of(2018, Month.APRIL, 1);
		Income income3 = generator.generate(Income.class, user, date3);
		income3.setId(null);
		entityManager.persist(income3);
		LocalDate date4 = LocalDate.of(2018, Month.JUNE, 30);
		Income income4 = generator.generate(Income.class, user, date4);
		income4.setId(null);
		entityManager.persist(income4);

		List<Income> result = incomeService.findIncomes(user, new PaymentPeriod(2018, Quarter.II));

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.APRIL));
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.MAY));
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.JUNE));
		assertFalse(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.JULY));
	}

}
