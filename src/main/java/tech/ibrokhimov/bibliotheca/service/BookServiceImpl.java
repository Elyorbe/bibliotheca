package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.Author;
import tech.ibrokhimov.bibliotheca.model.Book;
import tech.ibrokhimov.bibliotheca.model.BookRepository;
import tech.ibrokhimov.bibliotheca.model.User;

@Service
public class BookServiceImpl implements BookService{

	private BookRepository bookRepository;
	private AuthorService authorService;
	
	public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
		this.bookRepository = bookRepository;
		this.authorService = authorService;
	}
	
	@Override
	@Transactional
	public Book save(Book book, User actioner) {
		
		Optional<Author> optAuthor = authorService.findByFirstNameAndLastName(book.getAuthor().getFirstName(), book.getAuthor().getLastName());
		if(optAuthor.isPresent()) {
			book.setAuthor(optAuthor.get());
		} else {
			Author author = authorService.save(book.getAuthor(), actioner);
			book.setAuthor(author);
		}
		
		book.setCreator(actioner);
		book.setUpdater(actioner);
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}
}
