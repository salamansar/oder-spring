package org.salamansar.oder.module.payments.adapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.envbuild.environment.DbEnvironmentBuilder;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.AbstractPaymentModuleIntegrationTest;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Salamansar
 */
public class TaxAdapterImplIT extends AbstractPaymentModuleIntegrationTest {
	@Autowired
	private TaxAdapter taxAdapter;
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
	public void getAllTaxes() {
		Integer year = 2018;
		transactionTemplate.execute(ts -> {
			envBuilder.setParent(LocalDate.of(year, Month.FEBRUARY, 18))
						.createObject(Income.class).alias("income1")
					.setParent(LocalDate.of(year, Month.MARCH, 31))
						.createObject(Income.class).alias("income2")
					.setParent(LocalDate.of(year, Month.JULY, 1))
						.createObject(Income.class, false).alias("income3");
			Income income3 = envBuilder.getEnvironment().getByAlias("income3");
			income3.setAmount(BigDecimal.valueOf(350000));
			envBuilder.getDomainPersister().persistDomain(income3);
			return null;
		});
		
		List<TaxRowDto> result = taxAdapter.findAllTaxesForYear(user, year);
		
		assertNotNull(result);
		assertEquals(5, result.size());
		
		TaxRowDto firstTaxRow = result.get(0);
		assertNotNull(firstTaxRow);
		assertNotNull(firstTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.I), firstTaxRow.getPaymentPeriod());
		assertNotNull(firstTaxRow.getHealthInsuranceTaxAmount());
		assertNotNull(firstTaxRow.getIncomesTaxAmount());
		assertNull(firstTaxRow.getOnePercentTaxAmount());
		assertNotNull(firstTaxRow.getPensionTaxAmount());
		assertNotNull(firstTaxRow.getIncomesAmount());

		TaxRowDto secondTaxRow = result.get(1);
		assertNotNull(secondTaxRow);
		assertNotNull(secondTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.II), secondTaxRow.getPaymentPeriod());
		assertNotNull(secondTaxRow.getHealthInsuranceTaxAmount());
		assertNull(secondTaxRow.getIncomesTaxAmount());
		assertNull(secondTaxRow.getOnePercentTaxAmount());
		assertNotNull(secondTaxRow.getPensionTaxAmount());
		assertNull(secondTaxRow.getIncomesAmount());

		TaxRowDto thirdTaxRow = result.get(2);
		assertNotNull(thirdTaxRow);
		assertNotNull(thirdTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.III), thirdTaxRow.getPaymentPeriod());
		assertNotNull(thirdTaxRow.getHealthInsuranceTaxAmount());
		assertNotNull(thirdTaxRow.getIncomesTaxAmount());
		assertNull(thirdTaxRow.getOnePercentTaxAmount());
		assertNotNull(thirdTaxRow.getPensionTaxAmount());
		assertNotNull(thirdTaxRow.getIncomesAmount());

		TaxRowDto fourthTaxRow = result.get(3);
		assertNotNull(fourthTaxRow);
		assertNotNull(fourthTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.IV), fourthTaxRow.getPaymentPeriod());
		assertNotNull(fourthTaxRow.getHealthInsuranceTaxAmount());
		assertNull(fourthTaxRow.getIncomesTaxAmount());
		assertNull(fourthTaxRow.getOnePercentTaxAmount());
		assertNotNull(fourthTaxRow.getPensionTaxAmount());
		assertNull(fourthTaxRow.getIncomesAmount());
		
		TaxRowDto fifthTaxRow = result.get(4);
		assertNotNull(fifthTaxRow);
		assertNotNull(fifthTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.YEAR), fifthTaxRow.getPaymentPeriod());
		assertNotNull(fifthTaxRow.getHealthInsuranceTaxAmount());
		assertNotNull(fifthTaxRow.getIncomesTaxAmount());
		assertNotNull(fifthTaxRow.getOnePercentTaxAmount());
		assertNotNull(fifthTaxRow.getPensionTaxAmount());
		assertNotNull(fifthTaxRow.getIncomesAmount());
	}
	
}
