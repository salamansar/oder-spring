package org.salamansar.oder.core.utils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Salamansar
 */
public class ListBuilder<T> {
	private final List<T> accumulator = new LinkedList<>();
	
	public ListBuilder<T> and(List<T> elems) {
		accumulator.addAll(elems);
		return this;
	}
	
	public ListBuilder<T> and(T elem) { 
		accumulator.add(elem);
		return this;
	}
	
	public List<T> build() {
		return accumulator;
	}
	
	public static <T> ListBuilder<T> of(List<T> elems) {
		return new ListBuilder<T>()
				.and(elems);
	}
	
}
