package com.revature.rideforce.user.config;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.Base64URL;

import net.minidev.json.JSONObject;

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
	
	@Bean
	public ImmutableJWKSet<SecurityContext> produceJWKSource(CognitoConfig cc) throws IOException, ParseException {
		// Generate our signing key
		RSAKey key = new RSAKey.Builder(modulus, publicExponent)
				.privateExponent(privateExponent)
				.keyUse(KeyUse.SIGNATURE)
				.keyID(id)
				.build();
		
		// Get Cognito public keys
		List<JWK> l = new ArrayList<>(JWKSet.load(cc.getJwks()).getKeys());
		
		// Add our signing key to the list
		l.add(key);
		
		// Return the complete JWKSet
		return new ImmutableJWKSet<SecurityContext>(new JWKSet(l));
	}
	
	@Bean
	public JSONObject producePublicJWKSet(ImmutableJWKSet<SecurityContext> jwkSet) {
		// Return the public JWKSet
		return jwkSet.getJWKSet().toPublicJWKSet().toJSONObject();
	}
}
