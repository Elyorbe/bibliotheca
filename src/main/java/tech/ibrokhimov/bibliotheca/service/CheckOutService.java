package tech.ibrokhimov.bibliotheca.service;

import java.time.LocalDate;
import java.util.List;

import tech.ibrokhimov.bibliotheca.model.CheckOut;
import tech.ibrokhimov.bibliotheca.model.User;

public interface CheckOutService {
	
	CheckOut processIssue(String callNumber, User borrower);
	CheckOut processReturn(String callNumber);
	
	List<CheckOut> findByDueDate(LocalDate dueDate);
	
}
