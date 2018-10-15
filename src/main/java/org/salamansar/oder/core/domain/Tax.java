package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Salamansar
 */
@Data
@NoArgsConstructor
public class Tax implements Serializable {
	private PaymentPeriod period;
	private BigDecimal payment;
	private TaxCategory catgory;

	public Tax(TaxCategory catgory) {
		this.catgory = catgory;
	}
	
}
