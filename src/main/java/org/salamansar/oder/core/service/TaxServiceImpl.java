package org.salamansar.oder.core.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.mapper.TaxMapper;
import org.salamansar.oder.core.utils.ListBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class TaxServiceImpl implements TaxService {
	@Autowired
	private OnePercentTaxCalculator onePercentCalculator;
	@Autowired
	private FixedPaymentTaxCalculator fixedPaymentCalculator;
	@Autowired
	private IncomeTaxCalculator incomesTaxCalculator;
	@Autowired
	private DeductsCalculator deductsCalculator;
	@Autowired
	private TaxMapper taxMapper;

	@Override
	public List<Tax> calculateTaxes(User user, PaymentPeriod period) {
		return calculateTaxes(user, period, new TaxCalculationSettings());
	}

	@Override
	public List<Tax> calculateTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		List<Tax> incomeTaxes = incomesTaxCalculator.calculateIncomeTaxes(user, period, settings);
		List<Tax> fixedPayments = fixedPaymentCalculator.calculateFixedPayments(period, settings);
		List<Tax> onePersentPayments = onePercentCalculator.calculateOnePercentTaxes(user, period, settings);
		return ListBuilder.of(incomeTaxes)
				.and(fixedPayments)
				.and(onePersentPayments)
				.build();
	}

	@Override
	public List<TaxDeduction> calculateDeductions(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		return deductsCalculator.calculateDeducts(user, period, settings);
	}

	@Override
	public List<DeductibleTax> calculateDeductedTaxes(User user, PaymentPeriod period) {
		return calculateDeductedTaxes(user, period, TaxCalculationSettings.defaults());
	}
	
	@Override
	public List<DeductibleTax> calculateDeductedTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		TaxToDeductiableMapper mapper = getMapper(user, period, settings);
		List<Tax> rawTaxes = calculateTaxes(user, period, settings);
		return rawTaxes.stream()
				.map(mapper::map)
				.collect(Collectors.toList());
	}
	
	private TaxToDeductiableMapper getMapper(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		Map<PaymentPeriod, TaxDeduction> deductions = calculateDeductions(user, period, settings).stream()
				.collect(Collectors.toMap(TaxDeduction::getPeriod, Function.identity()));
		return new TaxToDeductiableMapper(deductions);
	}
	
	private class TaxToDeductiableMapper {

		private final Map<PaymentPeriod, TaxDeduction> deductions;

		public TaxToDeductiableMapper(Map<PaymentPeriod, TaxDeduction> deductions) {
			this.deductions = deductions;
		}

		public DeductibleTax map(Tax tax) {
			DeductibleTax deductible = taxMapper.mapToDeductible(tax);
			if (tax.getCatgory() == TaxCategory.INCOME_TAX) {
				TaxDeduction deduction = deductions.get(tax.getPeriod());
				if (deduction != null) {
					deductible.setDeduction(deduction.getDeduction());
				}
			}
			return deductible;
		}

	}
	
}
