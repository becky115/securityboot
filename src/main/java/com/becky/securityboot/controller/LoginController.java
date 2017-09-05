package com.becky.securityboot.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = "login/login.do")
	public String login_index(Locale locale, Model model){;
		return "login/login";
	}
	
	@RequestMapping(value = "/login/success.do", method = RequestMethod.GET)
	public String login_success(Locale locale, Model model) {
		
		return "login/success";
	}
	
}
