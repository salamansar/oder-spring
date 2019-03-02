package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
public class TaxDeduction {
	private PaymentPeriod period;
	private BigDecimal  deduction;
}
