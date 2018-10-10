package com.revature.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.revature.rideforce.user")
@EnableTransactionManagement
@ComponentScan("com.revature.rideforce.user")
public class TestConfiguration {

}
