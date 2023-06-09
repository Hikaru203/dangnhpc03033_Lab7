package com.example.demo.bai5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfigLogin implements WebMvcConfigurer {
	@Autowired
	AuthInterceptor auth;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auth)
			.addPathPatterns("/account/edit", "/account/chgpwd", "/order/**", "/admin/**")
			.excludePathPatterns("/assets/**");
	}
}
