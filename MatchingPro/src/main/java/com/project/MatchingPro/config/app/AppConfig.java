package com.project.MatchingPro.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/image/**").addResourceLocations("file:///C:/Users/admin/Desktop/test/");	//학원
		registry.addResourceHandler("/image/**").addResourceLocations("file:///C:/Users/user/Desktop/test/");	//집
	}
}
