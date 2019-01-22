package org.salamansar.oder.module.payments.controller;

/**
 *
 * @author Salamansar
 */
public enum TaxFormAttribute {
	TAXES_LIST("taxes"),
	AVAILABLE_YEARS_LIST("years"),
	SELECTED_YEAR("selectedYear");
	
	private final String attributeName;

	TaxFormAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}
}
