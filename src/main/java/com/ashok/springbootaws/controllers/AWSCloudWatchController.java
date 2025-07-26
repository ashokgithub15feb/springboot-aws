package com.ashok.springbootaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.springbootaws.services.AmazonCloudWatchService;

@RestController
@RequestMapping("/aws/v1/cloudwatch")
public class AWSCloudWatchController {

	@Autowired
	private AmazonCloudWatchService amazonCloudWatchService;
	
	@PostMapping(path = "/publish/{message}")
	public ResponseEntity<String> logMessageToCloudWatch(@PathVariable String message) {
		
		amazonCloudWatchService.logMessageToCloudWatch(message);
		
		return new ResponseEntity<String>("Message logged to AWS CloudWatch", HttpStatus.OK);
	}
}
