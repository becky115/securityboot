package com.becky.securityboot.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.becky.securityboot.domain.UserDomain;
import com.becky.securityboot.service.UserService;
import com.becky.securityboot.util.RequestUtil;


@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//update success
		logger.info("success login: " + authentication.getName() +","+ authentication.isAuthenticated());
		
		
		UserDomain userDomain = userService.select(authentication.getName());
		userService.setUserDomain(request.getSession(), userDomain);
		
		
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
			json.put("url", request.getContextPath());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		RequestUtil.responseWrite(response, json);
		//super.onAuthenticationSuccess(request, response, authentication);
		
		
	}
}
