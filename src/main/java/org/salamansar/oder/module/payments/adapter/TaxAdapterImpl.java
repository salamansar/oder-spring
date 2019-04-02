package org.salamansar.oder.module.payments.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.salamansar.oder.core.adapter.Adapter;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
import org.salamansar.oder.core.service.TaxService;
import org.salamansar.oder.core.utils.ListBuilder;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.comparator.Comparators;

/**
 *
 * @author Salamansar
 */
@Adapter
public class TaxAdapterImpl implements TaxAdapter {
	@Autowired
	private TaxService taxService;
	@Autowired
	private IncomeService incomeService;
	
	@Override
	public List<TaxRowDto> findAllTaxesForYear(User user, Integer year, boolean roundUp) {
		List<TaxRowDto> quarterTaxes = findTaxesForYear(user, year, roundUp);
		TaxRowDto summary = calculateSummary(quarterTaxes);
		TaxRowDto allYear = findTaxForPeriod(user, new PaymentPeriod(year, Quarter.YEAR), roundUp);
		return ListBuilder.of(quarterTaxes)
				.and(summary)
				.and(allYear)
				.build();
	}

	@Override
	public List<TaxRowDto> findTaxesForYear(User user, Integer year, boolean roundUp) {
		TaxCalculationSettings settings = new TaxCalculationSettings()
				.splitByQuants(true)
				.withRoundUp(roundUp);
		PaymentPeriod paymentPeriod = new PaymentPeriod(year, Quarter.YEAR);
		List<DeductibleTax> taxDomains = taxService.calculateDeductedTaxes(user, paymentPeriod, settings);
		List<TaxRowDto> taxRows = mapToTaxRows(paymentPeriod, taxDomains);
		List<QuarterIncome> incomes = incomeService.findQuarterIncomes(user, paymentPeriod, true);
		mergeWithIncomes(taxRows, incomes);
		return taxRows;
	}

	private List<TaxRowDto> mapToTaxRows(PaymentPeriod period, List<DeductibleTax> taxDomains) {
		Map<PaymentPeriod, TaxRowDto> rows = taxDomains.stream()
				.collect(Collectors.groupingBy(Tax::getPeriod))
				.entrySet().stream()
				.map(e -> mapToTaxRow(e.getKey(), e.getValue()))
				.collect(Collectors.toMap(TaxRowDto::getPaymentPeriod, v -> v));
		fixMissingRows(period, rows);
		return rows.values().stream()
				.sorted((v1, v2) -> v1.getPaymentPeriod().getQuarter().compareTo(v2.getPaymentPeriod().getQuarter()))
				.collect(Collectors.toList());
	}
	
	private TaxRowDto mapToTaxRow(PaymentPeriod period, List<DeductibleTax> taxes) {
		TaxRowDto dto = new TaxRowDto();
		dto.setPaymentPeriod(period);
		for(DeductibleTax tax : taxes) {
			switch(tax.getCatgory()) {
				case HEALTH_INSURANCE: dto.setHealthInsuranceTaxAmount(tax.getPayment()); break;
				case INCOME_TAX: {
					dto.setIncomesTaxAmount(tax.getPayment()); 
					dto.setIncomesDeductedTaxAmount(tax.getDeductedPayment());
					break;
				}
				case PENSION_INSURANCE: dto.setPensionTaxAmount(tax.getPayment()); break;
				case PENSION_PERCENT: dto.setOnePercentTaxAmount(tax.getPayment()); break;
			}
		}
		return dto;
	}
	
	private void fixMissingRows(PaymentPeriod period, Map<PaymentPeriod, TaxRowDto> rows) {
		for(Quarter quarter : Quarter.quarters()) {
			PaymentPeriod quarterPeriod = period.asQuarter(quarter);
			if(!rows.containsKey(quarterPeriod)) {
				rows.put(quarterPeriod, createStubTax(quarterPeriod));
			}
		}
	}
	
	private TaxRowDto createStubTax(PaymentPeriod paymentPeriod) {
		TaxRowDto dto = new TaxRowDto();
		dto.setPaymentPeriod(paymentPeriod);
		return dto;
	}
	
	private void mergeWithIncomes(List<TaxRowDto> taxRows, List<QuarterIncome> incomes) {
		Map<PaymentPeriod, BigDecimal> incomesMapping = incomes.stream()
				.collect(Collectors.toMap(QuarterIncome::getPeriod, QuarterIncome::getIncomeAmount));
		for(TaxRowDto dto : taxRows) {
			dto.setIncomesAmount(incomesMapping.get(dto.getPaymentPeriod()));
		}
	}
	
	private TaxRowDto calculateSummary(List<TaxRowDto> quarterTaxes) {
		return quarterTaxes.stream()
				.collect(Collectors.reducing(new TaxRowDto(), (accum, val) -> {
					accum.setHealthInsuranceTaxAmount(combineValues(accum.getHealthInsuranceTaxAmount(), val.getHealthInsuranceTaxAmount()));
					accum.setIncomesAmount(combineValues(accum.getIncomesAmount(), val.getIncomesAmount()));
					accum.setIncomesDeductedTaxAmount(combineValues(accum.getIncomesDeductedTaxAmount(), val.getIncomesDeductedTaxAmount()));
					accum.setIncomesTaxAmount(combineValues(accum.getIncomesTaxAmount(), val.getIncomesTaxAmount()));
					accum.setOnePercentTaxAmount(combineValues(accum.getOnePercentTaxAmount(), val.getOnePercentTaxAmount()));
					accum.setPensionTaxAmount(combineValues(accum.getPensionTaxAmount(), val.getPensionTaxAmount()));
					return accum;
				}));
	}
	
	private BigDecimal combineValues(BigDecimal accum, BigDecimal val) {
		if(accum == null) {
			if(val == null) {
				return null;
			} else {
				return val;
			}
		} else {
			if(val == null) {
				return accum;
			} else {
				return accum.add(val);
			}
		}
	}

	@Override
	public TaxRowDto findTaxForPeriod(User user, PaymentPeriod paymentPeriod, boolean roundUp) {
		TaxCalculationSettings settings = new TaxCalculationSettings()
				.withRoundUp(roundUp);
		List<DeductibleTax> taxDomains = taxService.calculateDeductedTaxes(user, paymentPeriod, settings);
		TaxRowDto taxRow = mapToTaxRow(paymentPeriod, taxDomains);
		QuarterIncome income = incomeService.findSingleIncome(user, paymentPeriod);
		taxRow.setIncomesAmount(income == null ? null : income.getIncomeAmount());
		return taxRow;
	}
	
	@Override
	public List<Integer> findYearsWithIncomes(User user) {
		return incomeService.findYearsWithIncomes(user).stream()
				.sorted(Comparators.comparable().reversed())
				.collect(Collectors.toList());
	}

}
