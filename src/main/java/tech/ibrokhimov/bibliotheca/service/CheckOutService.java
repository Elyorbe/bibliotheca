package tech.ibrokhimov.bibliotheca.service;

import tech.ibrokhimov.bibliotheca.model.CheckOut;
import tech.ibrokhimov.bibliotheca.model.User;

public interface CheckOutService {
	
	CheckOut processIssue(String callNumber, User borrower);
	CheckOut processReturn(String callNumber);
	
}
