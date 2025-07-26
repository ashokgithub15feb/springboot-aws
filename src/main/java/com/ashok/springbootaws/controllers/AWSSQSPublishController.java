package com.ashok.springbootaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.AmazonSQSAsync;

@RestController
@RequestMapping("/aws/v1/sqs")
public class AWSSQSPublishController {

	@Autowired
	@Qualifier("publish")
	private AmazonSQSAsync amazonSQSAsync;
	
	@Value("${sqs.url}")
	private String sqsUrl;
	
	@PostMapping("/publish/{message}")
	public ResponseEntity<String> postMessage(@PathVariable String message) {
		
		amazonSQSAsync.sendMessage(sqsUrl, message);
		
		return new ResponseEntity<String>("Message Published", HttpStatus.OK);
	}
}
