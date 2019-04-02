package org.salamansar.oder.module.payments.controller;

/**
 *
 * @author Salamansar
 */
public enum TaxFormAttribute {
	AVAILABLE_YEARS_LIST("years"),
	TAX_SINGLE("tax"),
	TAXES_LIST("taxes"),
	ROUND_UP_SIGN("roundUp"),
	SELECTED_QUARTER("selectedQuarter"),
	SELECTED_YEAR("selectedYear");
	
	private final String attributeName;

	TaxFormAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	public String attributeName() {
		return attributeName;
	}
}
