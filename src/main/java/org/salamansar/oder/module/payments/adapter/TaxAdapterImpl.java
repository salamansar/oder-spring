package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.salamansar.oder.core.adapter.Adapter;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.TaxService;
import org.salamansar.oder.core.utils.ListBuilder;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Salamansar
 */
@Adapter
public class TaxAdapterImpl implements TaxAdapter {
	@Autowired
	private TaxService taxService;
	
	@Override
	public List<TaxRowDto> findAllTaxesForYear(User user, Integer year) {
		List<TaxRowDto> quarterTaxes = findTaxesForYear(user, year);
		TaxRowDto summary = findSummarizedTaxesForYear(user, year);
		return ListBuilder.of(quarterTaxes)
				.and(summary)
				.build();
	}

	@Override
	public List<TaxRowDto> findTaxesForYear(User user, Integer year) {
		TaxCalculationSettings settings = new TaxCalculationSettings()
				.splitByQuants(true);
		PaymentPeriod paymentPeriod = new PaymentPeriod(year, Quarter.YEAR);
		List<Tax> taxDomains = taxService.calculateTaxes(user, paymentPeriod, settings);
		return mapToTaxRows(paymentPeriod, taxDomains);
	}

	private List<TaxRowDto> mapToTaxRows(PaymentPeriod period, List<Tax> taxDomains) {
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
	
	private TaxRowDto mapToTaxRow(PaymentPeriod period, List<Tax> taxes) {
		TaxRowDto dto = new TaxRowDto();
		dto.setPaymentPeriod(period);
		for(Tax tax : taxes) {
			switch(tax.getCatgory()) {
				case HEALTH_INSURANCE: dto.setHealthInsuranceTaxAmount(tax.getPayment()); break;
				case INCOME_TAX: dto.setIncomesTaxAmount(tax.getPayment()); break;
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

	@Override
	public TaxRowDto findSummarizedTaxesForYear(User user, Integer year) {
		PaymentPeriod paymentPeriod = new PaymentPeriod(year, Quarter.YEAR);
		List<Tax> taxDomains = taxService.calculateTaxes(user, paymentPeriod, TaxCalculationSettings.defaults());
		return mapToTaxRow(paymentPeriod, taxDomains);
	}

}
