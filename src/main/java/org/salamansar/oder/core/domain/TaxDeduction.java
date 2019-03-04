package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Salamansar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxDeduction {
	private PaymentPeriod period;
	private BigDecimal  deduction;
}
