package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import tech.ibrokhimov.bibliotheca.model.Book;
import tech.ibrokhimov.bibliotheca.model.User;

public interface BookService {
	
	Book save(Book book, User actioner);
	
	Optional<Book> findByTitle(String title);
}
