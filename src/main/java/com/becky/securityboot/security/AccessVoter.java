package com.becky.securityboot.security;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.becky.securityboot.domain.UserDomain;
import com.becky.securityboot.service.MenuService;
import com.becky.securityboot.service.UserService;

@Component
public class AccessVoter implements AccessDecisionVoter<Object>{
	
	private static final Logger logger = LoggerFactory.getLogger(AccessVoter.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		String userId = authentication.getName();
		UserDetails user = null;
		Object principal = authentication.getPrincipal();
		
		FilterInvocation fI = (FilterInvocation) object;
		String url = fI.getRequestUrl();
		

		
		
		
		logger.info("principal" + principal);
		logger.info("vote url>>>> " + url);
		//if(attributes.isEmpty()){
			if(principal instanceof UserDetails){
				user = (UserDetails) principal;
				logger.info("userId:  " + userId);
				
				
				HttpSession session = fI.getRequest().getSession();
				System.out.println("session" + session);
				System.out.println("menuService" + menuService);
				System.out.println("userService" + userService);
				UserDomain userDomain = userService.getUserDomain(session);
				System.out.println(userDomain);
				Integer groupSeq = null;
				if(userDomain != null){
					groupSeq = userDomain.getGroupSeq();
					
					
					boolean test = groupSeq == null ? false:menuService.checkAuth(groupSeq, url);
					logger.info("test " + test);
					System.out.println("AccessDecisionVoter" + AccessDecisionVoter.ACCESS_GRANTED);
					if(test){
						return AccessDecisionVoter.ACCESS_GRANTED;
					}else{
						return AccessDecisionVoter.ACCESS_DENIED;
					}
				}
	
			}
	//	}

		return AccessDecisionVoter.ACCESS_GRANTED;
	}


}
