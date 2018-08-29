package org.salamansar.oder.module.payments.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
	public String taxes() {
		return "listTaxes";
	}
	
}
