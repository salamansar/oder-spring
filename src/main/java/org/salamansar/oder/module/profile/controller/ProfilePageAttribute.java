package org.salamansar.oder.module.profile.controller;

/**
 *
 * @author Salamansar
 */
public enum ProfilePageAttribute {
	YEARS_INCOME("yearsIncomes");
	
	private final String attributeName;

	private ProfilePageAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
}
