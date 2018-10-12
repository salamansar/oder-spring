package org.salamansar.oder.core.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component
public class PaymentPeriodCalculatorImpl implements PaymentPeriodCalculator {

	@Override
	public PaymentPeriod calculatePeriod(Date date) {
		LocalDateTime local = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return calculatePeriod(local);
	}

	@Override
	public PaymentPeriod calculatePeriod(LocalDate date) {
		return new PaymentPeriod(date.getYear(), getQuarter(date));
	}

	@Override
	public PaymentPeriod calculatePeriod(LocalDateTime date) {
		return calculatePeriod(date.toLocalDate());
	}
	
	private Quarter getQuarter(LocalDate date) {
		int quarterNum = (date.getMonth().getValue() - 1) / 3 + 1;
		return Quarter.fromNumber(quarterNum);
	}
}
