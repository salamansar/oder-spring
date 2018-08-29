package org.salamansar.oder.core.utils;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salamansar
 */
public class ListBuilderTest {

	@Test
	public void accumulatesListsAndValues() {
		Integer val1 = 1;
		Integer val2 = 2;
		Integer val3 = 3;
		Integer val4 = 4;
		Integer val5 = 5;
		Integer val6 = 6;
		
		List<Integer> result = ListBuilder.of(Arrays.asList(val1, val2))
				.and(val3)
				.and(Arrays.asList(val4, val5))
				.and(val6)
				.build();
		
		assertNotNull(result);
		assertEquals(6, result.size());
		assertSame(val1, result.get(0));
		assertSame(val2, result.get(1));
		assertSame(val3, result.get(2));
		assertSame(val4, result.get(3));
		assertSame(val5, result.get(4));
		assertSame(val6, result.get(5));
	}
	
}
