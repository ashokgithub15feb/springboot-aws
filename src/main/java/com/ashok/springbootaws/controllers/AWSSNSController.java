package com.ashok.springbootaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.springbootaws.services.AmazonSNSService;

@RestController
@RequestMapping("/aws/v1/sns")
public class AWSSNSController {

	@Autowired
	private AmazonSNSService amazonSNSService;
	
	@PostMapping("/publish")
	public ResponseEntity<String> publishMail(@RequestBody List<String> payload) {
		amazonSNSService.publishTopic(payload.get(0), payload.get(1));
		
		return new ResponseEntity<String>("Message Published", HttpStatus.OK);
	}
}
