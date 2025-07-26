package com.ashok.springbootaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.springbootaws.exceptions.CustomAmazonException;
import com.ashok.springbootaws.model.User;
import com.ashok.springbootaws.services.AmazonDynamoDBService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/aws/v1/dynamo/db")
public class AWSDynamoDbController {

	@Autowired
	public AmazonDynamoDBService amazonDynamoDBService;
	
	@PostMapping("/add")
	public ResponseEntity<User> addRecord(@RequestBody User user) {
		return new ResponseEntity<>(amazonDynamoDBService.addRecord(user), HttpStatus.OK);
	}
	
	@PostMapping("/savejson/{currentId}")
	public ResponseEntity<String> saveJson(@PathVariable Integer currentId) throws JsonProcessingException {
		amazonDynamoDBService.saveJson(currentId);
		return new ResponseEntity<>("Json Saved", HttpStatus.OK);
	}
	
	@GetMapping("/agebetween/start/{start}/end/{end}")
	public ResponseEntity<List<User>> ageBetween(@PathVariable Integer start,
			@PathVariable Integer end) {
		return new ResponseEntity<>(amazonDynamoDBService.ageBetween(start, end), HttpStatus.OK);
	}
	
	@GetMapping("agebetween/with/email/{start}/{end}/{email}")
	public ResponseEntity<List<User>> ageBetweenWithEmail(@PathVariable Integer start,
			@PathVariable Integer end, @PathVariable String email) {
		return new ResponseEntity<>(amazonDynamoDBService.ageBetweenWithEmail(start, end, email), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable String id) throws CustomAmazonException {
		return new ResponseEntity<>(amazonDynamoDBService.update(user, id), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> delete(@PathVariable String userId)
	{
		amazonDynamoDBService.delete(userId);
		return new ResponseEntity<>("Record deleted",HttpStatus.OK);
	}
}
