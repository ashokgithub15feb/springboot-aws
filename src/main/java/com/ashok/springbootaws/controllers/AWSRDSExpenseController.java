package com.ashok.springbootaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.springbootaws.model.Expense;
import com.ashok.springbootaws.repository.ExpenseRepository;

@RestController
@RequestMapping("/aws/v1/rds/expense")
public class AWSRDSExpenseController {

	@Autowired
	public ExpenseRepository expenseRepository;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Expense expense) {
		System.out.println("Incoming Expense: "+expense);
		
		expenseRepository.save(expense);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
	
	@GetMapping("/findall")
	public ResponseEntity<?> findAllExpense() {
		System.out.println("findAllExpense Expense: ");
		Iterable<Expense> iterable = expenseRepository.findAll();
		for(Expense expense : iterable) {
			System.out.println(expense);
		}
		
		return new ResponseEntity<>(iterable, HttpStatus.OK);
	}
	
//	@GetMapping("/findByItem")
//	public ResponseEntity<?> findByItem() {
//		System.out.println("findByItem Expense: ");
//		Iterable<Expense> iterable = expenseRepository.findByItem();
//		for(Expense expense : iterable) {
//			System.out.println(expense);
//		}
//		return new ResponseEntity<>(iterable, HttpStatus.OK);
//	}
//	
//	@GetMapping("/price/over/{amount}")
//	public ResponseEntity<?> listItemsWithPriceOver(@PathVariable float amount) {
//		System.out.println("listItemsWithPriceOver Expense: ");
//		Iterable<Expense> iterable = expenseRepository.listItemsWithPriceOver(amount);
//		for(Expense expense : iterable) {
//			System.out.println(expense);
//		}
//		return new ResponseEntity<>(iterable, HttpStatus.OK);
//	}
}
