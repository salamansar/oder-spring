package org.salamansar.oder.core.component;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
public interface IncomeTaxMapStrategy {
	List<Tax> map(List<Income> incomes);
}
