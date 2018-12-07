package org.salamansar.oder.core.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.Income;

/**
 *
 * @author Salamansar
 */
public class IncomeUtilsTest {
	private RandomGenerator generator = new RandomGenerator();
	private IncomeUtils utils = new IncomeUtils();
	
	@Test
	public void summarizeIncomes() {
		Income income1 = generator.generate(Income.class);
		Income income2 = generator.generate(Income.class);
		
		BigDecimal result = utils.incomesSum(Arrays.asList(income1, income2));
		
		assertNotNull(result);
		assertTrue(income1.getAmount().add(income2.getAmount()).compareTo(result) == 0);
	}
	
	@Test
	public void summarizeEmptyIncomes() {
		BigDecimal result = utils.incomesSum(Collections.emptyList());
		
		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
	
}
