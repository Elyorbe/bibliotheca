package tech.ibrokhimov.bibliotheca.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tech.ibrokhimov.bibliotheca.model.Author;
import tech.ibrokhimov.bibliotheca.model.Book;
import tech.ibrokhimov.bibliotheca.model.BookFormat;
import tech.ibrokhimov.bibliotheca.model.BookItem;
import tech.ibrokhimov.bibliotheca.model.CheckOut;
import tech.ibrokhimov.bibliotheca.model.User;
import tech.ibrokhimov.bibliotheca.service.BookItemService;
import tech.ibrokhimov.bibliotheca.service.CheckOutService;
import tech.ibrokhimov.bibliotheca.service.UserService;

@Controller
@RequestMapping(Mappings.ADMIN + "/book")
public class BookManageController {
	
	private BookItemService bookItemService;
	private CheckOutService checkOutService;
	private UserService userService;
	
	public BookManageController(BookItemService bookItemService, CheckOutService checkOutService, UserService userService) {
		this.bookItemService = bookItemService;
		this.checkOutService = checkOutService;
		this.userService = userService;
	}

	@GetMapping("/list-all")
	public ModelAndView list() {
		
		final ModelAndView mav = new ModelAndView();

		Page<BookItem> booksPage = bookItemService.findAll(PageRequest.of(0, 20));
		populate(mav, booksPage);

		mav.addObject("activeTotal", "active");
		mav.setViewName("book/list_all");
		
		return mav;
	}
	
	@GetMapping("/create")
	public ModelAndView create() {
		
		final ModelAndView mav = new ModelAndView();
		
		mav.addObject("activeAdd", "active");
		mav.setViewName("book/create");
		
		return mav;
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public void create(HttpServletResponse response,
			@RequestParam(value = "authFirstName") final String _authFirstName,
			@RequestParam(value = "authLastName") final String _authLastName, 
			@RequestParam(value = "edition") final String _edition,
			@RequestParam(value = "pubDate") @DateTimeFormat(pattern ="yyyy-MM-dd") final LocalDate publicationDate,
			@RequestParam(value = "title") final String _title, 
			@RequestParam(value = "subject") final String _subject, 
			@RequestParam(value = "publisher") final String _publisher, 
			@RequestParam(value = "language") final String _language, 
			@RequestParam(value = "pages") final Integer pages,
			@RequestParam(value = "isbn") final String _iSBN, 
			@RequestParam(value = "format") final BookFormat format,
			@RequestParam(value = "rackNumber") final Integer rackNumber,
			@RequestParam(value = "classNumber") final String classNumber,
			@RequestParam(value = "purchaseDate", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") final LocalDate purchaseDate,
			@RequestParam(value = "price", required = false) final String _price,
			@RequestParam(value = "locationIdentifier", required = false) final String locationIdentifier) throws IOException {
		
		String authFirstName = _authFirstName.trim();
		String authLastName = _authLastName.trim();
		String title = _title.trim();
		String edition = _edition.trim();
		String subject = _subject.trim();
		String publisher = _publisher.trim();
		String language = _language.trim();
		String iSBN = _iSBN.trim();
		String price = _price.trim();
		
		Author author = new Author(authFirstName, authLastName);
		Book book = new Book.Builder(author, title).language(language).pages(pages).
				subject(subject).publisher(publisher).build();
		
		BookItem bookItem = new BookItem.Builder(edition, iSBN).publicationDate(publicationDate)
				.format(format).rackNumber(rackNumber).locationIdentifier(locationIdentifier).price(price)
				.book(book).build();
		bookItemService.save(bookItem, classNumber, User.getAdminUser());
//		return ResponseEntity.ok(RestResponse.ok());
		response.sendRedirect("/a/book/list-all");
	}
	
	@GetMapping("/issue")
	public ModelAndView issue() {
		
		final ModelAndView mav = new ModelAndView();
		
		mav.addObject("activeIssue", "active");
		mav.setViewName("book/issue");
		
		return mav;
	}
	
	@PostMapping("/issue")
	public ModelAndView issue( HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "username") final String _username,
			@RequestParam(value = "callNumber") final String _callNumber) throws IOException {
		
		final ModelAndView mav = new ModelAndView();
		
		String username = _username.trim();
		String callNumber = _callNumber.trim();
		
		CheckOut checkOut;
		Optional<User> optUser = userService.findByUsername(username);
		
		if(optUser.isPresent()) {
			checkOut = checkOutService.processIssue(callNumber, optUser.get());
			mav.addObject("issueMessage", "Return date: ");
			mav.addObject("date", checkOut.getDueDate());
			
		}
		
		mav.setViewName("book/response");
		
		return mav;
	}
	
	@GetMapping("/return")
	public ModelAndView returnBook() {
		
		final ModelAndView mav = new ModelAndView();
		
		mav.addObject("activeReturn", "active");
		mav.setViewName("book/return");
		
		return mav;
	}
	
	@PostMapping("/return")
	public ModelAndView returnBook( HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "callNumber") final String _callNumber) throws IOException {

		final ModelAndView mav = new ModelAndView();
		
		String callNumber = _callNumber.trim();
		CheckOut checkout = checkOutService.processReturn(callNumber);
	
		mav.addObject("returnMessage", "Amount of fine to be paid: ");
		mav.addObject("amount", checkout.getFine().multiply(BigDecimal.valueOf(1000)));
		mav.setViewName("book/response");
				
		return mav;
	}
	
	@GetMapping("/result")
	public ModelAndView result(HttpServletResponse response, HttpServletRequest request) {
		
		final ModelAndView  mav = new ModelAndView();
		
		
		Object returnDate = request.getAttribute("returnDate");
		Object fine = request.getAttribute("fine");
		
		if(returnDate != null) {
			mav.addObject("issueMessage", "Return date: ");
			mav.addObject("date", returnDate);
		}
		
		if(fine != null) {
			mav.addObject("returnMessage", "Amount of fine to be paid: ");
			mav.addObject("amount", fine);
		}
		
		mav.setViewName("book/response");
		
		return mav;
	}
	
	private void populate(ModelAndView mav, Page<BookItem> booksPage) {
		
		mav.addObject("pageNo", booksPage.getNumber());
		mav.addObject("totalPages", booksPage.getSize());
		
		mav.addObject("hasNextPages", booksPage.getNumber() < booksPage.getTotalPages() - 1);
		mav.addObject("hasPreviousPages", booksPage.getNumber() > 0);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for(BookItem book : booksPage) {
			Map<String, Object> map = new HashMap<>();
			map.put("title", book.getBook().getTitle());
			map.put("author", book.getBook().getAuthor().getFirstName() +"  " +  book.getBook().getAuthor().getLastName());
			map.put("isbn", book.getiSBN());
			map.put("edition", book.getEditon());
			map.put("format", book.getFormat());
			map.put("status", book.getStatus());
			map.put("callNumber", book.getCallNumber());
			
			list.add(map);
		}
		
		mav.addObject("list", list);
	}
	
 }
