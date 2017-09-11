package com.becky.securityboot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.becky.securityboot.handler.AuthFailureHandler;
import com.becky.securityboot.handler.AuthSuccessHandler;
import com.becky.securityboot.security.AccessVoter;
import com.becky.securityboot.security.LoginAuthenticationProvider;
import com.becky.securityboot.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_PAGE = "/login/login.do";
	private static final String LOGOUT_URL = "/logout";
	private static final String LOGOUT_SUCCESS_URL = "/";
	
	@Autowired
	private AuthFailureHandler failureHandler;
	
	@Autowired
	private AuthSuccessHandler successHandler;
	
	@Autowired
	private UserService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private AccessVoter accessVorter;
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		LoginAuthenticationProvider authenticationProvider = new LoginAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setHideUserNotFoundExceptions(false);
		return authenticationProvider;
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager(){
		List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
		
		RoleVoter rv = new RoleVoter();
		rv.setRolePrefix("");
		
		decisionVoters.add(new AuthenticatedVoter());
		decisionVoters.add(rv);
		decisionVoters.add(new WebExpressionVoter());
		decisionVoters.add(accessVorter);
		//decisionVoters.add(new AccessVoter());

		return new UnanimousBased(decisionVoters);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
						"/css/**",
						"/webjars/**",
						"/images/**",
						"/js/**").permitAll()
				.antMatchers("/**")
					.access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
					.anyRequest().authenticated()
					.accessDecisionManager(accessDecisionManager())
					.and()
//				.sessionManagement() 
//					.maximumSessions(1)
//					.expiredUrl("")
//					.maxSessionsPreventsLogin(false)
//					.and()
//				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//					.invalidSessionUrl("")
//					.and()
			.formLogin()
				.loginPage(LOGIN_PAGE)
				.permitAll()
				.loginProcessingUrl(LOGIN_PROCESSING_URL)
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.and()
			.logout()
				.logoutUrl(LOGOUT_URL)
				.invalidateHttpSession(true)
				.logoutSuccessUrl(LOGOUT_SUCCESS_URL)
				.permitAll()
				.and()
			.csrf();
		
	}
//	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**");
	}
}
	