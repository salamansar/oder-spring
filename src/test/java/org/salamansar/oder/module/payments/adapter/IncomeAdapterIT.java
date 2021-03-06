package org.salamansar.oder.module.payments.adapter;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.envbuild.environment.DbEnvironmentBuilder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.AbstractWebAppIntegrationTest;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Salamansar
 */
public class IncomeAdapterIT extends AbstractWebAppIntegrationTest {
	@Autowired
	private IncomeAdapter adapter;
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	private User user;
	
	@Before
	public void setUp() {
		transactionTemplate.execute(ts -> {
			envBuilder.newBuild()
					.createObject(User.class).alias("user").asParent();
			user = envBuilder.getEnvironment().getByAlias("user");
			return null;
			
		});
	}

	@Test
	public void getAllIncomes() {
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.now())
					.createObject(Income.class).alias("income1")
					.createObject(Income.class).alias("income2");
			return null;
		});
		Income income1 = envBuilder.getEnvironment().getByAlias("income1");
		Income income2 = envBuilder.getEnvironment().getByAlias("income2");
		
		List<IncomeDto> result = adapter.getAllIncomes(user);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<IncomeDto> income1Dto = result.stream().filter(i -> i.getId().equals(income1.getId())).findAny();
		assertTrue(income1Dto.isPresent());
		checkIncomeDto(income1, income1Dto.get());
		Optional<IncomeDto> income2Dto = result.stream().filter(i -> i.getId().equals(income2.getId())).findAny();
		assertTrue(income2Dto.isPresent());
	}
	
	private void checkIncomeDto(Income income1, IncomeDto income1Dto) {
		assertEquals(income1.getId(), income1Dto.getId());
		assertEquals(income1.getDescription(), income1Dto.getDescription());
		assertEquals(income1.getDocumentNumber(), income1Dto.getDocumentNumber());
		assertTrue(income1.getAmount().compareTo(income1Dto.getAmount()) == 0);
		assertNotNull(income1Dto.getIncomeDate());
	}
	
	@Test
	public void getAllYearIncomes() {
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.of(2018, Month.MARCH, 5))
						.createObject(Income.class).alias("income1")
					.setParent(LocalDate.of(2018, Month.OCTOBER, 16))
						.createObject(Income.class).alias("income2")
					.setParent(LocalDate.of(2019, Month.JANUARY, 16))
						.createObject(Income.class).alias("income3");
			return null;
		});
		Income income1 = envBuilder.getEnvironment().getByAlias("income1");
		Income income2 = envBuilder.getEnvironment().getByAlias("income2");
		Income income3 = envBuilder.getEnvironment().getByAlias("income3");

		List<QuarterIncomeDto> result = adapter.getAllYearIncomes(user);

		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<QuarterIncomeDto> income1Dto = result.stream().filter(i -> i.getPeriod().equals(new PaymentPeriod(2018, Quarter.YEAR))).findAny();
		assertTrue(income1Dto.isPresent());
		assertTrue(income1.getAmount().add(income2.getAmount()).compareTo(income1Dto.get().getIncomeAmount()) == 0);
		Optional<QuarterIncomeDto> income2Dto = result.stream().filter(i -> i.getPeriod().equals(new PaymentPeriod(2019, Quarter.YEAR))).findAny();
		assertTrue(income2Dto.isPresent());
		assertTrue(income3.getAmount().compareTo(income2Dto.get().getIncomeAmount()) == 0);
	}
	
	@Test
	public void addIncome() {
		IncomeDto dto = envBuilder.getRandomGenerator().generate(IncomeDto.class);
		
		Long result = adapter.addIncome(user, dto);
		
		assertNotNull(result);
		Income domain = transactionTemplate.execute(ts -> {			
			return entityManager.find(Income.class, result);
		});
		
		assertNotNull(domain);
		checkDomain(dto, domain);
	}
	
	@Test
	public void editIncome() {
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.now())
					.createObject(Income.class).alias("income");
			return null;
		});
		Income domain = envBuilder.getEnvironment().getByAlias("income");
		IncomeDto dto = envBuilder.getRandomGenerator().generate(IncomeDto.class);
		dto.setId(domain.getId());
		
		adapter.editIncome(user, dto);
		
		Income updated = transactionTemplate.execute(ts -> {			
			return entityManager.find(Income.class, domain.getId());
		});
		
		assertNotNull(updated);
		checkDomain(dto, updated);
	}
	
	@Test
	public void deleteIncome() {
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.now())
					.createObject(Income.class).alias("income");
			return null;
		});
		Income domain = envBuilder.getEnvironment().getByAlias("income");
		
		adapter.deleteIncome(user, domain.getId());
		
		Income updated = transactionTemplate.execute(ts -> {			
			return entityManager.find(Income.class, domain.getId());
		});
		
		assertNull(updated);
	}

	private void checkDomain(IncomeDto dto, Income domain) {
		assertNotNull(domain.getId());
		assertEquals(dto.getDescription(), domain.getDescription());
		assertEquals(dto.getDocumentNumber(), domain.getDocumentNumber());
		assertTrue(dto.getAmount().compareTo(domain.getAmount()) == 0);
		assertNotNull(domain.getIncomeDate());
	}
	
	@Test
	public void getIncome() {
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.now())
					.createObject(Income.class).alias("income");
			return null;
		});
		Income income = envBuilder.getEnvironment().getByAlias("income");
		
		IncomeDto result = adapter.getIncome(user, income.getId());
		
		checkIncomeDto(income, result);
	}
	
	@Test
	public void getIncomeWithNotExistsDomain() {
		IncomeDto result = adapter.getIncome(user, -1L);

		assertNull(result);
	}
	
	
}
