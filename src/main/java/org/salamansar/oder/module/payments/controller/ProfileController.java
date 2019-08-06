package org.salamansar.oder.module.payments.controller;

import org.salamansar.oder.module.payments.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

	@GetMapping
	public String getProfilePage(Model model) {
		UserDto user = new UserDto(); //todo: fill real data
		user.setFirstName("Иван");
		user.setMiddleName("Иванович");
		user.setLastName("Иванов");
		user.setLogin("ivan");
		model.addAttribute("user", user);
		return "profile";
	}
	
}
