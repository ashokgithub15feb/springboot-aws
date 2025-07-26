package com.ashok.springbootaws.confiig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;

@Configuration
public class AWSCloudWatchConfig {

	@Value("${accessKey}")
	private String accessKey;

	@Value("${secretKey}")
	private String secretKey;

	@Value("${region}")
	private String region;
	
	public AWSCredentials awsCredentials() {
		
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		
		return awsCredentials;
	}
	
	@Bean
	public AWSLogs awsLogs() {
		AWSLogs awsLogs = AWSLogsClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
				.withRegion(region)
				.build();

		return awsLogs;
	}
}
