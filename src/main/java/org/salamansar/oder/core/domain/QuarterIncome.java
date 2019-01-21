package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
public class QuarterIncome {
	private PaymentPeriod period;
	private BigDecimal incomeAmount;
}
