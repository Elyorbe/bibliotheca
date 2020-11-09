package tech.ibrokhimov.bibliotheca.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.BookItem;
import tech.ibrokhimov.bibliotheca.model.BookItemRepository;
import tech.ibrokhimov.bibliotheca.model.BookStatus;
import tech.ibrokhimov.bibliotheca.model.CheckOut;
import tech.ibrokhimov.bibliotheca.model.CheckOutRepository;
import tech.ibrokhimov.bibliotheca.model.User;

@Service
public class CheckOutServiceImpl implements CheckOutService{

	private CheckOutRepository checkOutRepository;
	private BookItemRepository bookItemRepository;
	
	public CheckOutServiceImpl(CheckOutRepository checkOutRepository, BookItemRepository bookItemRepository) {
		this.checkOutRepository = checkOutRepository;
		this.bookItemRepository = bookItemRepository;
	}
	
	@Override
	@Transactional
	public CheckOut processIssue(String callNumber, User borrower) {
		
		CheckOut checkOut = new CheckOut();
		
		BookItem bookItem = bookItemRepository.findByCallNumber(callNumber);
		bookItem.setStatus(BookStatus.LOANED);
		bookItemRepository.save(bookItem);
		
		checkOut.setBookItem(bookItem);
		checkOut.setBorrower(borrower);
		checkOut.setCheckOutDate(LocalDate.now());
		checkOut.setDueDate(LocalDate.now().plusDays(10));
	
		return checkOutRepository.save(checkOut);
	}

	@Override
	@Transactional
	public CheckOut processReturn(String callNumber) {		
		
		BookItem bookItem = bookItemRepository.findByCallNumber(callNumber);
		bookItem.setStatus(BookStatus.AVAILABLE);
		bookItemRepository.save(bookItem);
		
		CheckOut checkOut = checkOutRepository.findTopByBookItem_Id(bookItem.getId());
		LocalDate returnDate = LocalDate.now();
		long borrowDays = ChronoUnit.DAYS.between(checkOut.getCheckOutDate(), returnDate);
		
		if(borrowDays > 10) {
			long extraDays = borrowDays - 10;
			checkOut.setFine(BigDecimal.valueOf(extraDays));
		}
		
		checkOut.setFine(BigDecimal.valueOf(0));
		checkOut.setReturnDate(returnDate);
		
		
		return checkOutRepository.save(checkOut);
	}
	
	
}
