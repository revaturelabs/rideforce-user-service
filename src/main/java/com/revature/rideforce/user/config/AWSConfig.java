package com.revature.rideforce.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

@Configuration
@ConfigurationProperties("aws")
public class AWSConfig {
	private String accessKey, secretKey;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	@Bean
	public AWSStaticCredentialsProvider produceAWSCredentials() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
	}
}
