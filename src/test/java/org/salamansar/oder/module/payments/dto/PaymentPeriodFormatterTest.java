package org.salamansar.oder.module.payments.dto;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
@RunWith(Parameterized.class)
public class PaymentPeriodFormatterTest {
	
	@Parameterized.Parameter(0)
	public PaymentPeriod paymentPeriod;
	@Parameterized.Parameter(1)
	public String expectedString;
	private PaymentPeriodFormatter formatter;
	
	@Before
	public void setUp() {
		formatter = new PaymentPeriodFormatter(paymentPeriod);
	}
	
	
	@Test
	public void gettingYear() {
		assertEquals(paymentPeriod.getYear(), formatter.getYear());
	}
	
	@Test
	public void gettingQuarter() {
		assertEquals(paymentPeriod.getQuarter().getNumberValue(), formatter.getQuarter().intValue());
	}
	
	@Test
	public void formattedString() {
		assertEquals(expectedString, formatter.getFormattedMessage());
	}
	
	@Parameterized.Parameters
	public static List<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{new PaymentPeriod(2018, Quarter.I), "I квартал 2018 года"},
			{new PaymentPeriod(2018, Quarter.II), "II квартал 2018 года"},
			{new PaymentPeriod(2019, Quarter.III), "III квартал 2019 года"},
			{new PaymentPeriod(2019, Quarter.IV), "IV квартал 2019 года"},
			{new PaymentPeriod(2019, Quarter.YEAR), "2019 год"}
		});
	}
	
	
	
}
