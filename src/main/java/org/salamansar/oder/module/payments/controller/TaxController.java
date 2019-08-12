package org.salamansar.oder.module.payments.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.common.FillUserInfo;
import org.salamansar.oder.security.CurrentUser;
import org.salamansar.oder.module.payments.adapter.TaxAdapter;
import org.salamansar.oder.module.payments.dto.PaymentPeriodFormatter;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("payments/taxes")
@Slf4j
public class TaxController {
	@Autowired
	private TaxAdapter adapter;
	
	@GetMapping
	public String taxes() {
		Integer currentYear = LocalDate.now().getYear();
		return "redirect:taxes/" + currentYear;
	}

	@GetMapping("/{year}")
	@FillUserInfo
	public String taxesForYear(
			@PathVariable("year") Integer year, 
			@RequestParam(name = "roundUp", required = false) Boolean roundUp, 
			@CurrentUser User user,
			Model model) {
		loadTaxes(user, year, model, roundUp);
		loadYears(user, year, model);
		return "listTaxes";
	}
	
	@GetMapping("/{year}/{quarter}")
	@FillUserInfo
	public String taxesForQuarter(
			@PathVariable("year") Integer year, 
			@PathVariable("quarter") Integer quarter, 
			@RequestParam(name = "roundUp", required = false) Boolean roundUp, 
			@CurrentUser User user,
			Model model) {
		//todo: check parameters
		PaymentPeriod period = new PaymentPeriod(year, Quarter.fromNumber(quarter));
		loadSingleTax(user, period, roundUp, model);
		setPaymentPeriod(period, model);
		return "singleTax";
	}

	private void loadTaxes(User user, Integer year, Model model, Boolean roundUp) {
		List<TaxRowDto> taxRows = adapter.findAllTaxesForYear(user, year, roundUp != null && roundUp);
		model.addAttribute(TaxFormAttribute.TAXES_LIST.attributeName(), taxRows);
		model.addAttribute(TaxFormAttribute.ROUND_UP_SIGN.attributeName(), roundUp);
	}

	private void loadYears(User user, Integer year, Model model) {
		model.addAttribute(TaxFormAttribute.SELECTED_YEAR.attributeName(), year);
		model.addAttribute(TaxFormAttribute.AVAILABLE_YEARS_LIST.attributeName(), adapter.findYearsWithIncomes(user));
	}

	private void loadSingleTax(User user, PaymentPeriod paymentPeriod, Boolean roundUp, Model model) {
		TaxRowDto taxRow = adapter.findTaxForPeriod(
				user,
				paymentPeriod, 
				roundUp != null && roundUp
		);
		model.addAttribute(TaxFormAttribute.TAX_SINGLE.attributeName(), taxRow);
		model.addAttribute(TaxFormAttribute.ROUND_UP_SIGN.attributeName(), roundUp);
	}
	
	private void setPaymentPeriod(PaymentPeriod period, Model model) {
		model.addAttribute(TaxFormAttribute.PAYMENT_PERIOD_INFO.attributeName(), new PaymentPeriodFormatter(period));
	}
}
