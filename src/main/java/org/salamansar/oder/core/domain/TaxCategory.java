package org.salamansar.oder.core.domain;

/**
 *
 * @author Salamansar
 */
public enum TaxCategory {
	PENSION_INSURANCE(1),
	HEALTH_INSURANCE(2),
	INCOME_TAX(3),
	PENSION_PERCENT(4);
	
	private final long id;

	TaxCategory(int id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
}
