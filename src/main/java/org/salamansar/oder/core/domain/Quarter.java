package org.salamansar.oder.core.domain;

import java.util.EnumSet;

/**
 *
 * @author Salamansar
 */
public enum Quarter {
    I(1),
    II(2),
    III(3),
    IV(4),
    YEAR(0);
    
    int value;

    Quarter(int value) {
        this.value = value;
    }
    
	public static Quarter fromNumber(int num) {
		return EnumSet.allOf(Quarter.class).stream()
				.filter(q -> q.value == num)
				.findFirst()
				.orElse(null);
	}
	
}
