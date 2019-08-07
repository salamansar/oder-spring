package org.salamansar.oder.module.common;

/**
 *
 * @author Salamansar
 */
public enum CommonFormAttribute {
	USER("user");
	
	private final String attributeName;

	CommonFormAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	public String attributeName() {
		return attributeName;
	}
}
