package org.salamansar.oder.module.profile.controller;

import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.common.CommonFormAttribute;
import org.salamansar.oder.module.common.FillUserInfo;
import org.salamansar.oder.module.profile.dto.UserDto;
import org.salamansar.oder.module.profile.mapper.UserMapper;
import org.salamansar.oder.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private UserMapper mapper;

	@GetMapping
	@FillUserInfo
	public String getProfilePage(Model model, @CurrentUser User user) {
		return "profile";
	}
	
}
