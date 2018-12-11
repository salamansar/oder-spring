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
	
	public TaxCalculationSettings splitByQuants(Boolean byQuants) {
		this.byQuants = byQuants;
		return this;
	}
	
	public TaxCalculationSettings withRoundUp(Boolean roundUp) {
		this.roundUp = roundUp;
		return this;
	}
	
	public TaxCalculationSettings withDeducts(Boolean includeDeducts) {
		this.includeDeducts = includeDeducts;
		return this;
	}
	
}