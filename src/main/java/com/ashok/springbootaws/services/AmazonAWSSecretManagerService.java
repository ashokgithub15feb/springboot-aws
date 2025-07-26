package com.ashok.springbootaws.services;

import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;

public class AmazonAWSSecretManagerService {

	public static AWSCredentials credentials() {
		AWSCredentials awsCredentials = new BasicAWSCredentials("accessKey", "secretKey");
		
		return awsCredentials;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AWSSecretsManager clientManager = AWSSecretsManagerClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials()))
				.withRegion("us-east-1")
				.build();
		
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId("myProdSecret");
		GetSecretValueResult getSecretValueResult = null;
		
		getSecretValueResult = clientManager.getSecretValue(getSecretValueRequest);
		
		String secretValue = getSecretValueResult.getSecretString();
		System.out.println("AWS Secret Value: "+secretValue);
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(secretValue, Map.class);
		map.forEach((k, v) -> System.out.println("secret Key: "+k + "secret Value: "+v));
	}
}
