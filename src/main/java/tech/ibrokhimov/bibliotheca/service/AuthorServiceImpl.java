package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.Author;
import tech.ibrokhimov.bibliotheca.model.AuthorRepository;
import tech.ibrokhimov.bibliotheca.model.User;

@Service
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;
	
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	@Override
	@Transactional
	public Author save(Author author, User actioner) {
		author.setCreator(actioner);
		author.setUpdater(actioner);
		return authorRepository.save(author);
	}

	@Override
	@Transactional
	public Author update(Author author, User actioner) {
		author.setUpdater(actioner);
		return authorRepository.save(author);
	} 

	@Override
	public Optional<Author> findByFirstNameAndLastName(String firstName, String lastName) {
		return authorRepository.findByFirstNameAndLastName(firstName, lastName);
	}
	
}
