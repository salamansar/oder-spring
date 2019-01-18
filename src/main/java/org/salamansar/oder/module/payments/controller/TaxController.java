package org.salamansar.oder.module.payments.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("taxes")
@Slf4j
public class TaxController {

	@GetMapping
	public String taxes(Model model) {
		List<TaxRowDto> taxRows = new ArrayList<>(5);
		TaxRowDto first = new TaxRowDto();
		first.setHealthInsuranceTaxAmount(BigDecimal.valueOf(1460));
		first.setPensionTaxAmount(BigDecimal.valueOf(6537));
		first.setPaymentPeriod(new PaymentPeriod(2018, Quarter.I));
		taxRows.add(first);
		TaxRowDto second = new TaxRowDto();
		second.setIncomesAmount(BigDecimal.valueOf(250000));
		second.setIncomesTaxAmount(BigDecimal.valueOf(15000));
		second.setPensionTaxAmount(BigDecimal.valueOf(6537));
		second.setPaymentPeriod(new PaymentPeriod(2018, Quarter.II));
		taxRows.add(second);
		TaxRowDto third = new TaxRowDto();
		third.setIncomesAmount(BigDecimal.valueOf(400000));
		third.setIncomesTaxAmount(BigDecimal.valueOf(24000));
		third.setHealthInsuranceTaxAmount(BigDecimal.valueOf(1460));
		third.setPaymentPeriod(new PaymentPeriod(2018, Quarter.III));
		taxRows.add(third);
		TaxRowDto fourth = new TaxRowDto();
		fourth.setPaymentPeriod(new PaymentPeriod(2018, Quarter.IV));
		taxRows.add(fourth);
		TaxRowDto year = new TaxRowDto();
		year.setIncomesAmount(BigDecimal.valueOf(1100000));
		year.setIncomesTaxAmount(BigDecimal.valueOf(66000));
		year.setHealthInsuranceTaxAmount(BigDecimal.valueOf(5840));
		year.setPensionTaxAmount(BigDecimal.valueOf(26148));
		year.setOnePercentTaxAmount(BigDecimal.valueOf(8000));
		year.setPaymentPeriod(new PaymentPeriod(2018, Quarter.YEAR));
		taxRows.add(year);
		model.addAttribute("taxes", taxRows);
		return "listTaxes"; //todo: implement
	}

}
