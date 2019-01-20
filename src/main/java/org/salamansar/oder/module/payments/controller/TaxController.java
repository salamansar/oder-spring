package org.salamansar.oder.module.payments.controller;

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

	@GetMapping("/{year}")
	public String taxes(@PathVariable("year") Integer year, Model model) {
		User user = new User(); //todo: deal with getting user process
		user.setId(1L);
		List<TaxRowDto> taxRows = adapter.findAllTaxesForYear(user, year);
		model.addAttribute("taxes", taxRows);
		return "listTaxes";
	}

}
