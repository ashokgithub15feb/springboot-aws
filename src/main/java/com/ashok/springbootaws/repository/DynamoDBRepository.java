package com.ashok.springbootaws.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.ashok.springbootaws.model.User;

@EnableScan
public interface DynamoDBRepository extends CrudRepository<User, String>{

}
