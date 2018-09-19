package com.revature.rideshare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.revature.rideshare")
public class UserApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(UserApplication.class, args);
	}


}
