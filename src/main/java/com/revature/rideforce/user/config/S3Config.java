package com.revature.rideforce.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@ConfigurationProperties("aws.s3")
public class S3Config {
	private Regions region;
	private String bucketName;

	public Regions getRegion() {
		return region;
	}

	public void setRegion(Regions region) {
		this.region = region;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	/**
	 * Creates the S3 client.
	 * 
	 * @param acp the AWS credentials.
	 * @return the S3 client.
	 */
	@Bean
	public AmazonS3 produceS3Client(AWSStaticCredentialsProvider acp) {
		return AmazonS3ClientBuilder.standard()
				.withRegion(region)
				.withCredentials(acp)
				.build();
	}
}
