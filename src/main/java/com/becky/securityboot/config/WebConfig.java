package com.becky.securityboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	private static final String[] RESOURCE_LOCATIONS = {
			"classpath:/static/",
			"classpath:/resources/"
	};

	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "main/index.do");
		
		//registry.addViewController("/").setViewName("index"); @EnableWebMvc
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		registry.addResourceHandler("/**")
//				.addResourceLocations(RESOURCE_LOCATIONS)
//				.setCachePeriod(3600)
//				.resourceChain(true)
//				.addResolver(new PathResourceResolver());
//	}
}
