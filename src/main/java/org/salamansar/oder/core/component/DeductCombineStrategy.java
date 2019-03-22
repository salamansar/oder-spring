package org.salamansar.oder.core.component;

import java.math.BigDecimal;

/**
 *
 * @author Salamansar
 */
public interface DeductCombineStrategy {
	BigDecimal applyDeduct(BigDecimal tax, BigDecimal deduct);
}
