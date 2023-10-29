package com.prophius.socialmediaapi;

import com.prophius.socialmediaapi.filters.AuthFilters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SocialMediaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApiApplication.class, args);
	}
    @Bean
	public FilterRegistrationBean<AuthFilters> filterFilterRegistrationBean(){
		FilterRegistrationBean<AuthFilters> registrationBean=new FilterRegistrationBean<>();
		AuthFilters authFilter=new AuthFilters();
		registrationBean.setFilter(authFilter);
		// for protected path
		registrationBean.addUrlPatterns("/api/comments/*");
		registrationBean.addUrlPatterns("/api/posts/*");
		registrationBean.addUrlPatterns("/api/follow/*");
		registrationBean.addUrlPatterns("/api/users/auth/*");
		return registrationBean;
	}

}
