package org.salamansar.oder.core.domain;

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
    
    
}
