package com.revature.rideforce.user.config;

import java.net.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

@Configuration
@ConfigurationProperties("aws.cognito")
public class CognitoConfig {
	private URL jwks;
	private Regions region;
	private String clientId;
	
	public Regions getRegion() {
		return region;
	}
	
	public void setRegion(Regions region) {
		this.region = region;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public URL getJwks() {
		return jwks;
	}
	
	public void setJwks(URL jwks) {
		this.jwks = jwks;
	}
	
	/**
	 * Creates the Cognito client.
	 * 
	 * @param acp the AWS credentials.
	 * @return the Cognito client.
	 */
	@Bean
	public AWSCognitoIdentityProvider produceCognitoClient(AWSStaticCredentialsProvider acp) {
		return AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(acp)
				.withRegion(region)
				.build();
	}
}
