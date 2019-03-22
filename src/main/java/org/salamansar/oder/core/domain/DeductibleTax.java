package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Salamansar
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeductibleTax extends Tax {
	private BigDecimal deduction;
	private BigDecimal deductedPayment;
	
}
