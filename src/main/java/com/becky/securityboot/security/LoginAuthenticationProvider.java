package com.becky.securityboot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginAuthenticationProvider extends DaoAuthenticationProvider{
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);
	
	@Override
	public void setPasswordEncoder(Object passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}
	
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
	
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try{
			Authentication auth = super.authenticate(authentication);
			logger.info("auth... provider:" + auth.isAuthenticated());
			
			return auth;
		}catch(UsernameNotFoundException e){
			logger.info("error... provider UsernameNotFoundException " + e.getMessage());
			throw e;
		}catch (BadCredentialsException e) {
			logger.info("error... provider BadCredentialsException " + e.getMessage());
			//update bad
			throw e;
		}catch (LockedException e) {
			logger.info("error... provider LockedException " + e.getMessage());
			throw e;
		}catch (AccountExpiredException e) {
			logger.info("error... provider AccountExpiredException " + e.getMessage());
			throw e;
		}catch (Exception e) {
			logger.info("error... provider Exception " + e.getMessage());
			throw e;
		}
		
	}
	

}
