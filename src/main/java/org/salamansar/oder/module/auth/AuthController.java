package org.salamansar.oder.module.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("auth")
public class AuthController {

	@GetMapping("login")
	public String loginPage(
			@RequestParam(required = false) String error,
			Model model
	) {
		if(error != null) {
			model.addAttribute("error", true);
		}
		return "auth";
	}
	
}
