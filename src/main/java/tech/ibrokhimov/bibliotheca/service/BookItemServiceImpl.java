package tech.ibrokhimov.bibliotheca.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.Book;
import tech.ibrokhimov.bibliotheca.model.BookItem;
import tech.ibrokhimov.bibliotheca.model.BookItemRepository;
import tech.ibrokhimov.bibliotheca.model.BookStatus;
import tech.ibrokhimov.bibliotheca.model.User;

@Service
public class BookItemServiceImpl implements BookItemService{

	private BookService bookService;
	private BookItemRepository bookItemRepository;
	
	
	public BookItemServiceImpl(BookService bookService, BookItemRepository bookItemRepository) {
		this.bookService = bookService;
		this.bookItemRepository = bookItemRepository;
	}
	
	@Override
	public BookItem save(BookItem bookItem, String classNumber, User actioner) {
		
		Optional<Book> optBook = bookService.findByTitle(bookItem.getBook().getTitle());;
		if(optBook.isPresent()) {
			bookItem.setBook(optBook.get());
		} else {
			 Book book = bookService.save(bookItem.getBook(), actioner);
			 bookItem.setBook(book);
		}
		
		String callNumber = generateCallNumber(bookItem, classNumber);
		bookItem.setCallNumber(callNumber);
		bookItem.setStatus(BookStatus.AVAILABLE);
		bookItem.setCreator(actioner);
		bookItem.setUpdater(actioner);
		
		return bookItemRepository.save(bookItem);
	}
	
	
	
	@Override
	public Long count() {
		return bookItemRepository.count();
	}
	
	@Override
	public Long countByStatus(BookStatus status) {
		return bookItemRepository.countByStatus(status);
	}
	
	@Override
	public BookItem findByCallNumber(String callNumber) {
		return bookItemRepository.findByCallNumber(callNumber);
	}

	@Override
	public List<BookItem> findAll() {
		return bookItemRepository.findAll();
	}
	
	@Override
	public Page<BookItem> findAll(Pageable pageable) {
		return bookItemRepository.findAll(pageable);
	}

	/**
	 * Returns call number for the {@code bookItem}.<br>
	 * Uses {@code classNumber} as a prefix. After that
	 * first character of an {@code Author} is appended.<br>
	 * Then generate 3 digit random number and append it.Then
	 * append first character of {@code book's title} and edition number.<br>
	 * If there are more than one copies of the {@code bookItem} append {@code c.number
	 *  of copies}
	 *   
	 *  @return callNumber */
	private String generateCallNumber(BookItem bookItem, String classNumber) {
		
		Random random = new Random();
		int num = random.nextInt(900) + 100;
		String iSBN = bookItem.getiSBN();
		Long copies = bookItemRepository.countByiSBN(iSBN);
		
		StringBuilder callNumber = new StringBuilder(classNumber);
		StringBuilder secondSub = new StringBuilder();
		
		char authorNameFirstChar = bookItem.getBook().getAuthor().getLastName().charAt(0);
		char bookTitleFirstChar = Character.toLowerCase(bookItem.getBook().getTitle().charAt(0));
		char editionNum = bookItem.getEditon().charAt(0);
		
		secondSub.append(authorNameFirstChar);
		secondSub.append(num);
		secondSub.append(bookTitleFirstChar);
		secondSub.append(editionNum);
		callNumber.append(" ");
		
		if(copies > 0) {
			
			BookItem firstCopy = bookItemRepository.findFirstByiSBN(iSBN);
			String[] subs = firstCopy.getCallNumber().split(" ");
			String sameSub = subs[1].substring(0, 5);
			
			callNumber.append(sameSub);
			callNumber.append(editionNum);
			callNumber.append(" c.").append(copies + 1);
			
		} else {
			
			callNumber.append(secondSub);
		}
		return callNumber.toString();
	}
	
}
