package com.revature.rideforce.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.util.Base64URL;

@Configuration
@ConfigurationProperties("jwt.registration.rsa-key")
public class JWKConfig {
	private String id;
	private Base64URL modulus, publicExponent, privateExponent;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Base64URL getModulus() {
		return modulus;
	}
	
	public void setModulus(Base64URL modulus) {
		this.modulus = modulus;
	}
	
	public Base64URL getPublicExponent() {
		return publicExponent;
	}
	
	public void setPublicExponent(Base64URL publicExponent) {
		this.publicExponent = publicExponent;
	}
	
	public Base64URL getPrivateExponent() {
		return privateExponent;
	}
	
	public void setPrivateExponent(Base64URL privateExponent) {
		this.privateExponent = privateExponent;
	}
}
