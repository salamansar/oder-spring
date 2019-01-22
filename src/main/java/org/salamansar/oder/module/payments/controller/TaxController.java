package org.salamansar.oder.module.payments.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.adapter.TaxAdapter;
import org.salamansar.oder.module.payments.dto.TaxRowDto;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private TaxAdapter adapter;
	
	@GetMapping
	public String taxes() {
		Integer currentYear = LocalDate.now().getYear();
		return "redirect:taxes/" + currentYear;
	}

	@GetMapping("/{year}")
	public String taxesForYear(@PathVariable("year") Integer year, Model model) {
		User user = new User(); //todo: deal with getting user process
		user.setId(1L);
		loadTaxes(user, year, model);
		loadYears(user, year, model);
		return "listTaxes";
	}

	private void loadTaxes(User user, Integer year, Model model) {
		List<TaxRowDto> taxRows = adapter.findAllTaxesForYear(user, year);
		model.addAttribute(TaxFormAttribute.TAXES_LIST.getAttributeName(), taxRows);
	}

	private void loadYears(User user, Integer year, Model model) {
		model.addAttribute(TaxFormAttribute.SELECTED_YEAR.getAttributeName(), year);
		model.addAttribute(TaxFormAttribute.AVAILABLE_YEARS_LIST.getAttributeName(), Arrays.asList(2018, 2017, 2016)); //todo: load available years
	}

}
