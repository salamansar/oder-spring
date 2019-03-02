package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class DeductsCalculatorImpl implements DeductsCalculator {
	
	@Override
	public List<TaxDeduction> calculateDeducts(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		return null; //todo: implement
	}

}
