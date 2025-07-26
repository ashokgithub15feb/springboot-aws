package com.ashok.springbootaws.confiig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;

@Configuration
public class AWSSQSConfig {

	@Value("${accessKey}")
	private String accessKey;

	@Value("${secretKey}")
	private String secretKey;

	@Value("${region}")
	private String region;

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Bean
	@Qualifier("publish")
	@Primary
	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
	}

	@Bean
	public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
		SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
		factory.setAmazonSqs(amazonSQSAsync());
		return factory;
	}

	@Bean
	public QueueMessageHandler queueMessageHandler() {
		QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
		factory.setAmazonSqs(amazonSQSAsync());
		QueueMessageHandler handler = factory.createQueueMessageHandler();

		List<HandlerMethodArgumentResolver> list = new ArrayList<>();
		HandlerMethodArgumentResolver argumentResolver = new PayloadMethodArgumentResolver(
				new MappingJackson2MessageConverter());
		list.add(argumentResolver);

		handler.setArgumentResolvers(list);

		return handler;
	}
}
