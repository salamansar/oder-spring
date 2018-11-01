package org.salamansar.oder.core.domain;

import java.time.Month;
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

	public Month getStartMonth() {
		if(quarter == null) {
			return null;
		}
		switch (quarter) {
			case I:
				return Month.JANUARY;
			case II:
				return Month.APRIL;
			case III:
				return Month.JULY;
			case IV:
				return Month.OCTOBER;
			default:
				return Month.JANUARY;
		}
	}

	public Month getEndMonth() {
		if (quarter == null) {
			return null;
		}
		switch (quarter) {
			case I:
				return Month.MARCH;
			case II:
				return Month.JUNE;
			case III:
				return Month.SEPTEMBER;
			case IV:
				return Month.DECEMBER;
			default:
				return Month.DECEMBER;
		}
	}

}
