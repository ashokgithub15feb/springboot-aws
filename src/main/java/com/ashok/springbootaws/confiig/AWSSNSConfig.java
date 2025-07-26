package com.ashok.springbootaws.confiig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AWSSNSConfig {

	@Value("${accessKey}")
	private String accessKey;

	@Value("${secretKey}")
	private String secretKey;

	@Value("${region}")
	private String region;

	public AWSCredentials credentials() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return awsCredentials;
	}

	@Bean
	public AmazonSNS amazonSNS() {
		AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials()))
				.withRegion(region).build();
		
		return amazonSNS;
	}
}
