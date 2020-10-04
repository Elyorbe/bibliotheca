package tech.ibrokhimov.bibliotheca.service;

import tech.ibrokhimov.bibliotheca.model.BookItem;
import tech.ibrokhimov.bibliotheca.model.User;

public interface BookItemService {
	
	BookItem save(BookItem bookItem, String classNumber, User actioner);
	
}
