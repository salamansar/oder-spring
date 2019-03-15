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
	
	public BigDecimal getDeductedPayment() {
		if(getPayment() == null) {
			return null;
		}
		BigDecimal deducted = getAmount(getPayment()).subtract(getAmount(deduction));
		if(deducted.compareTo(BigDecimal.ZERO) > 0) {
			return deducted;
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	private BigDecimal getAmount(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}
	
}
