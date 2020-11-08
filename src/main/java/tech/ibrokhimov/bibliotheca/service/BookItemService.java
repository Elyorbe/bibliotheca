package tech.ibrokhimov.bibliotheca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tech.ibrokhimov.bibliotheca.model.BookItem;
import tech.ibrokhimov.bibliotheca.model.BookStatus;
import tech.ibrokhimov.bibliotheca.model.User;

public interface BookItemService {
	
	BookItem save(BookItem bookItem, String classNumber, User actioner);
	
	Long count();
	Long countByStatus(BookStatus status);
	
	BookItem findByCallNumber(String callNumber);
	
	List<BookItem> findAll();
	Page<BookItem> findAll(Pageable pageable);
}
