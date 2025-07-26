package com.ashok.springbootaws.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.ashok.springbootaws.exceptions.CustomAmazonException;
import com.ashok.springbootaws.model.Address;
import com.ashok.springbootaws.model.User;
import com.ashok.springbootaws.repository.DynamoDBRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmazonDynamoDBService {

	@Autowired
	private DynamoDBRepository dynamoDBRepository;
	
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public User addRecord(User user) {
		User savedUser = dynamoDBRepository.save(user);
		return savedUser;
	}
	
	public void saveJson(Integer currentId) throws JsonProcessingException {
		List<User> list = new ArrayList<>();
		for (int i = currentId + 1; i <= 10; i++) {
			User user = new User(String.valueOf(i), i + " txt");
			Address address = new Address(455001, 125 + i, "colony :" + i, "state MP", "India");
			ObjectMapper mapper = new ObjectMapper();
			user.setAddress(mapper.writeValueAsString(address));
			user.setAge(10 + i);
			user.setEmail("user" + i + "@hotmail.com");
			list.add(user);
		}
		dynamoDBRepository.saveAll(list);

	}

	public List<User> ageBetween(Integer start, Integer end) {
		
		AttributeValue minRange = new AttributeValue().withN(Integer.toString(start));
		AttributeValue maxRange = new AttributeValue().withN(Integer.toString(end));
		
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		Condition condition = new Condition().withComparisonOperator(ComparisonOperator.BETWEEN).withAttributeValueList(minRange, maxRange);
		
		Map<String, Condition> scanFilter = new HashMap<>();
		scanFilter.put("age", condition);
		dbScanExpression.setScanFilter(scanFilter);
		
		return dynamoDBMapper.scan(User.class, dbScanExpression);
	}
	
	public List<User> ageBetweenWithEmail(Integer start, Integer end, String email) {

		AttributeValue minRange = new AttributeValue().withN(Integer.toString(start));
		AttributeValue maxRange = new AttributeValue().withN(Integer.toString(end));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Condition condition = new Condition().withComparisonOperator(ComparisonOperator.BETWEEN)
				.withAttributeValueList(minRange, maxRange);
		Condition emailCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_CONTAINS)
				.withAttributeValueList(new AttributeValue().withS(email));

		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		scanFilter.put("age", condition);
		scanFilter.put("email", emailCondition);
		scanExpression.setScanFilter(scanFilter);

		return dynamoDBMapper.scan(User.class, scanExpression);
	}
	
	public User update(User user, String id) throws CustomAmazonException {
		
		Optional<User> findById = dynamoDBRepository.findById(id);
		if(findById.isPresent()) {
			User optionalUser = findById.get();
			optionalUser.setName(user.getName());
			optionalUser.setEmail(user.getEmail());
			optionalUser.setAge(user.getAge());
			optionalUser.setAddress(user.getAddress());
			
			User updatedUser = dynamoDBRepository.save(optionalUser);
			return updatedUser;
		} else {
			throw new CustomAmazonException(CustomAmazonException.UserNotFoundEception(id));
		}
	}

	public void delete(String userId) {
		dynamoDBRepository.deleteById(userId);
	}
}
