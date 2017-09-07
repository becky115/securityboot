package com.becky.securityboot.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@GetMapping(value = "test/index.do")
	public String textIndex(Locale locale, Model model){;
		return "test/view";
	}
	
	@GetMapping(value = "test/left.do")
	public String textLEft(Locale locale, Model model){;
		return "test/leftView";
	}
//	
}
