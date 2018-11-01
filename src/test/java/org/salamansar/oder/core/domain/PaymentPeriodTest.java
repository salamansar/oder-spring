package org.salamansar.oder.core.domain;

import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salamansar
 */
public class PaymentPeriodTest {
	
	@Test
	public void testMonthsForFirstQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.I);
		
		assertEquals(Month.JANUARY, period.getStartMonth());
		assertEquals(Month.MARCH, period.getEndMonth());
	}
	
	@Test
	public void testMonthsForSecondQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.II);
		
		assertEquals(Month.APRIL, period.getStartMonth());
		assertEquals(Month.JUNE, period.getEndMonth());
	}
	
	@Test
	public void testMonthsForThirdQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		
		assertEquals(Month.JULY, period.getStartMonth());
		assertEquals(Month.SEPTEMBER, period.getEndMonth());
	}
	
	@Test
	public void testMonthsForFourthQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.IV);
		
		assertEquals(Month.OCTOBER, period.getStartMonth());
		assertEquals(Month.DECEMBER, period.getEndMonth());
	}
	
	@Test
	public void testMonthsForYear() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		
		assertEquals(Month.JANUARY, period.getStartMonth());
		assertEquals(Month.DECEMBER, period.getEndMonth());
	}
	
	@Test
	public void testMonthsForNullValue() {
		PaymentPeriod period = new PaymentPeriod(2018, null);
		
		assertNull(period.getStartMonth());
		assertNull(period.getEndMonth());
	}
	
}
