package com.revature.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.revature.ridefore.user.repositories") //access the beans that are repos
@EnableTransactionManagement
@ComponentScan(basePackages = "com.revature"/*, excludeFilters=filterType.REGEX, pattern="com\\.revature\\.repository\\config\\.ProdRepoConfig")*/) //access beans in our base class

public class TestConfiguration {

}
