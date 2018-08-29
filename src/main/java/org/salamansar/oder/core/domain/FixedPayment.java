package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
public class FixedPayment {
	private Integer year;
	private BigDecimal value;
	private TaxCategory category;
}
