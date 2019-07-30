package org.salamansar.oder.module.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("auth")
public class AuthController {

	@GetMapping("login")
	public String loginPage() {
		return "auth";
	}
	
}
