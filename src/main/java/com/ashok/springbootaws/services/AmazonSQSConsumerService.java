package com.ashok.springbootaws.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class AmazonSQSConsumerService {

	private Logger logger = LogManager.getLogger(AmazonSQSConsumerService.class);
	
	@SqsListener(value = "my-trigger-destination-sqs-success-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void mySuccessConsumer(String message) {
		System.out.println("Success: Message Received in SYSOUT from Amazon SQS: "+message);
		logger.info("Success: Message Received from Amazon SQS: {}", message);
	}
	
	@SqsListener(value = "my-trigger-destination-sqs-failer-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void myFailureConsumer(String message) {
		System.out.println("Failure: Message Received in SYSOUT from Amazon SQS: "+message);
		logger.info("Failure: Message Received from Amazon SQS: {}", message);
	}
}
