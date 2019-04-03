package org.salamansar.oder.core.domain;

import java.util.EnumSet;
import java.util.Set;

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
	
	public int getNumberValue() {
		return value;
	}
	
    
	public static Quarter fromNumber(int num) {
		return EnumSet.allOf(Quarter.class).stream()
				.filter(q -> q.value == num)
				.findFirst()
				.orElse(null);
	}
	
	public static Set<Quarter> quarters() {
		return EnumSet.of(Quarter.I, Quarter.II, Quarter.III, Quarter.IV);
	}
}
