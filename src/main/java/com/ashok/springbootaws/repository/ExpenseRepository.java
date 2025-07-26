package com.ashok.springbootaws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashok.springbootaws.model.Expense;

//@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long>{

	//public List<Expense> findByItem();
	
	//@Query("SELECT e FROM Expense e WHERE e.amount >= :amount")
	//public List<Expense> listItemsWithPriceOver(@Param("amount") float amount);
}
