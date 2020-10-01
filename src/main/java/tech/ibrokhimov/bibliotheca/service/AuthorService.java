package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import tech.ibrokhimov.bibliotheca.model.Author;
import tech.ibrokhimov.bibliotheca.model.User;

public interface AuthorService {
	
	Author save(Author author, User actioner);
	Author update(Author author, User actioner);
	
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
