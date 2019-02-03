package com.revature.rideforce.user.config;

import java.net.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;

@Configuration
@ConfigurationProperties("aws.cognito")
public class CognitoConfig {
	private URL jwks;
	private String poolId;
	private Regions region;
	private String clientId;
	private String accessKey;
	private String secretKey;
	
	public String getPoolId() {
		return poolId;
	}
	
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	
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
	
	public URL getJwks() {
		return jwks;
	}
	
	public void setJwks(URL jwks) {
		this.jwks = jwks;
	}
	
	@Override
	public String toString() {
		return "CognitoConfig [jwks=" + jwks + ", poolId=" + poolId + ", region=" + region + ", accessKey=" + accessKey
				+ ", secretKey=" + secretKey + "]";
	}
}
