package org.salamansar.oder.core.domain;

import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
public class TaxCalculationSettings {
	private Boolean byQuants = false;
	private Boolean roundUp = false; //todo: use parameter
	private Boolean includeDeducts = false; //todo: use parameter
	
	public static TaxCalculationSettings defaults() {
		return new TaxCalculationSettings();
	}
	
}
