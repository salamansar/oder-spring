package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.salamansar.oder.core.utils.PaymentsUtils.getAmount;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.deductCombine.rounding")
public class RoundingDeductCombineStrategy implements DeductCombineStrategy {

	@Override
	public BigDecimal applyDeduct(BigDecimal tax, BigDecimal deduct) {
		if(tax == null) {
			return null;
		}
		BigDecimal taxAmount = getAmount(tax).setScale(0, RoundingMode.CEILING);
		BigDecimal deductAmount = getAmount(deduct).setScale(0, RoundingMode.FLOOR);
		BigDecimal deducted = taxAmount.subtract(deductAmount);
		if (deducted.compareTo(BigDecimal.ZERO) > 0) {
			return deducted;
		} else {
			return BigDecimal.ZERO;
		}
	}

}
