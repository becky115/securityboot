package com.becky.securityboot.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value = "main/index.do")
	public String login_index(Locale locale, Model model){;
		return "main/index";
	}
}
