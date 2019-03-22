package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import static org.salamansar.oder.core.utils.PaymentsUtils.getAmount;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.deductCombine.plain")
public class PlainDeductCombineStrategy implements DeductCombineStrategy {

	@Override
	public BigDecimal applyDeduct(BigDecimal tax, BigDecimal deduct) {
		if(tax == null) {
			return null;
		}
		BigDecimal deducted = getAmount(tax).subtract(getAmount(deduct));
		if (deducted.compareTo(BigDecimal.ZERO) > 0) {
			return deducted;
		} else {
			return BigDecimal.ZERO;
		}
	}
	
}
