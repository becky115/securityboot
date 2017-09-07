package com.becky.securityboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.becky.securityboot.interceptor.ThymeleafInterceptor;

@Configuration
@ImportResource("classpath:/application-context.xml")
public class WebConfig extends WebMvcConfigurerAdapter {
	
//	@Bean
//	public ThymeleafInterceptor thymeleafInterceptor(){
//		return new ThymeleafInterceptor();
//	}
	
	@Autowired
	private ThymeleafInterceptor thymeleafInterceptor;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "main/index.do");
		//registry.addViewController("/").setViewName("index"); @EnableWebMvc
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(thymeleafInterceptor);
	}
	
}
