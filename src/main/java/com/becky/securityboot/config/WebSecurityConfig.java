package com.becky.securityboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.becky.securityboot.handler.AuthFailureHandler;
import com.becky.securityboot.handler.AuthSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthFailureHandler failureHandler;
	
	@Autowired
	private AuthSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/**")
					.access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
					//.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login/login.do")
				.permitAll()
				.loginProcessingUrl("/login")
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.and()
			.logout()
				.permitAll()
				.and()
			.csrf()
				.disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	 


}
	