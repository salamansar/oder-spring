package org.salamansar.oder.core.component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
public class PaymentPeriodCalculatorImplTest {
	private PaymentPeriodCalculatorImpl calculator = new PaymentPeriodCalculatorImpl();

	@Test
	public void firstQuarter() {
		PaymentPeriod expected = new PaymentPeriod(2018, Quarter.I);
		LocalDateTime date = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
		
		PaymentPeriod result = calculator.calculatePeriod(date);
		
		assertNotNull(result);
		assertEquals(expected, result);
		
		LocalDateTime date2 = LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0);
		PaymentPeriod result2 = calculator.calculatePeriod(date2);
		
		assertNotNull(result2);
		assertEquals(expected, result2);
		
		LocalDateTime date3 = LocalDateTime.of(2018, Month.MARCH, 1, 0, 0);
		PaymentPeriod result3 = calculator.calculatePeriod(date3);
		
		assertNotNull(result3);
		assertEquals(expected, result3);
	}
	
	@Test
	public void secondQuarter() {
		PaymentPeriod expected = new PaymentPeriod(2000, Quarter.II);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(2000, 3, 1, 1, 0);
		
		PaymentPeriod result = calculator.calculatePeriod(calendar1.getTime());
		
		assertNotNull(result);
		assertEquals(expected, result);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2000, 4, 1, 1, 0);
		PaymentPeriod result2 = calculator.calculatePeriod(calendar2.getTime());
		
		assertNotNull(result2);
		assertEquals(expected, result2);
		
		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2000, 5, 1, 1, 0);
		PaymentPeriod result3 = calculator.calculatePeriod(calendar3.getTime());
		
		assertNotNull(result3);
		assertEquals(expected, result3);
	}
	
	@Test
	public void thirdQuarter() {
		PaymentPeriod expected = new PaymentPeriod(2020, Quarter.III);
		LocalDateTime date = LocalDateTime.of(2020, Month.JULY, 1, 0, 0);
		
		PaymentPeriod result = calculator.calculatePeriod(date);
		
		assertNotNull(result);
		assertEquals(expected, result);
		
		LocalDateTime date2 = LocalDateTime.of(2020, Month.AUGUST, 1, 0, 0);
		PaymentPeriod result2 = calculator.calculatePeriod(date2);
		
		assertNotNull(result2);
		assertEquals(expected, result2);
		
		LocalDateTime date3 = LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0);
		PaymentPeriod result3 = calculator.calculatePeriod(date3);
		
		assertNotNull(result3);
		assertEquals(expected, result3);
	}
	
	@Test
	public void fourthQuarter() {
		PaymentPeriod expected = new PaymentPeriod(2018, Quarter.IV);
		LocalDateTime date = LocalDateTime.of(2018, Month.OCTOBER, 1, 0, 0);
		
		PaymentPeriod result = calculator.calculatePeriod(date);
		
		assertNotNull(result);
		assertEquals(expected, result);
		
		LocalDateTime date2 = LocalDateTime.of(2018, Month.NOVEMBER, 1, 0, 0);
		PaymentPeriod result2 = calculator.calculatePeriod(date2);
		
		assertNotNull(result2);
		assertEquals(expected, result2);
		
		LocalDateTime date3 = LocalDateTime.of(2018, Month.DECEMBER, 1, 0, 0);
		PaymentPeriod result3 = calculator.calculatePeriod(date3);
		
		assertNotNull(result3);
		assertEquals(expected, result3);
	}
	
}
