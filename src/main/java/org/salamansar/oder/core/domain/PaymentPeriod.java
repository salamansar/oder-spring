package org.salamansar.oder.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
@AllArgsConstructor
public class PaymentPeriod {
	private Integer year;
	private Quarter quarter;
}
