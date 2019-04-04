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
	public void monthsForFirstQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.I);
		
		assertEquals(Month.JANUARY, period.getStartMonth());
		assertEquals(Month.MARCH, period.getEndMonth());
	}
	
	@Test
	public void monthsForSecondQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.II);
		
		assertEquals(Month.APRIL, period.getStartMonth());
		assertEquals(Month.JUNE, period.getEndMonth());
	}
	
	@Test
	public void monthsForThirdQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		
		assertEquals(Month.JULY, period.getStartMonth());
		assertEquals(Month.SEPTEMBER, period.getEndMonth());
	}
	
	@Test
	public void monthsForFourthQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.IV);
		
		assertEquals(Month.OCTOBER, period.getStartMonth());
		assertEquals(Month.DECEMBER, period.getEndMonth());
	}
	
	@Test
	public void monthsForYear() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		
		assertEquals(Month.JANUARY, period.getStartMonth());
		assertEquals(Month.DECEMBER, period.getEndMonth());
	}
	
	@Test
	public void monthsForNullValue() {
		PaymentPeriod period = new PaymentPeriod(2018, null);
		
		assertNull(period.getStartMonth());
		assertNull(period.getEndMonth());
	}
	
	@Test
	public void asQaurter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		
		PaymentPeriod result = period.asQuarter(Quarter.III);
		
		assertNotSame(period, result);
		assertEquals(2018, result.getYear().intValue());
		assertEquals(2018, period.getYear().intValue());
		assertEquals(Quarter.III, result.getQuarter());
		assertEquals(Quarter.YEAR, period.getQuarter());
	}
	
	@Test
	public void asYear() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		
		PaymentPeriod result = period.asYear(2016);
		
		assertNotSame(period, result);
		assertEquals(2016, result.getYear().intValue());
		assertEquals(2018, period.getYear().intValue());
		assertEquals(Quarter.YEAR, result.getQuarter());
		assertEquals(Quarter.YEAR, period.getQuarter());
	}
	
	@Test
	public void previousYear() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);

		PaymentPeriod result = period.previousYear();

		assertNotSame(period, result);
		assertEquals(2017, result.getYear().intValue());
		assertEquals(2018, period.getYear().intValue());
		assertEquals(Quarter.YEAR, result.getQuarter());
		assertEquals(Quarter.YEAR, period.getQuarter());
	}
	
	@Test
	public void nextYear() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);

		PaymentPeriod result = period.nextYear();

		assertNotSame(period, result);
		assertEquals(2019, result.getYear().intValue());
		assertEquals(2018, period.getYear().intValue());
		assertEquals(Quarter.YEAR, result.getQuarter());
		assertEquals(Quarter.YEAR, period.getQuarter());
	}
	
}
