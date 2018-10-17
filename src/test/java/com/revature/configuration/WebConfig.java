package com.revature.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan({ "test.controllers" })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {
   @Bean
   public MultipartResolver multipartResolver() {
       CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
       return multipartResolver;
   }
}