package com.ashok.springbootaws.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
public class AmazonSNSService {

	private Logger logger = LogManager.getLogger(AmazonSNSService.class);
	
	@Autowired
	private AmazonSNS amazonSNS;
	
	@Value("${topic.arn}")
	private String topicARN;
	
	public void publishTopic(String subject, String message) {
		try {
			logger.info("Inside publishTopic method");
			PublishRequest publishRequest = new PublishRequest(topicARN, message);
			publishRequest.setSubject(subject);
			PublishResult publish = amazonSNS.publish(publishRequest);
			logger.info("Amazon SQN published message: ()", publish);
		} catch (Exception e) {
			logger.error("Amazon SNS failed to publish the message: {}" , e.getMessage());
		}
	}
}
