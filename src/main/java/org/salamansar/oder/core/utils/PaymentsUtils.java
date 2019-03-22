package org.salamansar.oder.core.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
public class PaymentsUtils {
	
	public static <T> BigDecimal summarize(List<T> objects, Function<T, BigDecimal> mapFunction) {
		return objects.stream()
				.map(mapFunction)
				.reduce(BigDecimal.ZERO, 
						(accum, amount) -> accum.add(amount));
	}
	
	public static BigDecimal summarize(BigDecimal... values) {
		return Stream.of(values)
				.reduce(BigDecimal.ZERO,
						(accum, amount) -> accum.add(amount));
	}
	
	
	public static BigDecimal incomesSum(List<Income> incomes) {
		return summarize(incomes, income -> income.getAmount());
	}
	
	public static BigDecimal taxesSum(List<Tax> taxes) {
		return summarize(taxes, tax -> tax.getPayment());
	}
	
	public static BigDecimal fixedPaymentsSum(List<FixedPayment> payments) {
		return summarize(payments, payment -> payment.getValue());
	}
	
	public static BigDecimal getAmount(BigDecimal srcAmount) {
		return srcAmount == null ? BigDecimal.ZERO : srcAmount;
	}
	
}
