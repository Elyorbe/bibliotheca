package tech.ibrokhimov.bibliotheca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.CheckOut;

@Service
public class EmailService {
	
	private CheckOutService checkOutService;
	private JavaMailSender mailSender;

	
	public EmailService(CheckOutService checkOutService, JavaMailSender mailSender) {
		this.checkOutService = checkOutService;
		this.mailSender = mailSender;
	}
	
	@Scheduled(cron = "0 10 18 * * *")
	protected void test() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		List<CheckOut> dueBooks = checkOutService.findByDueDate(tomorrow);
		for(CheckOut ch : dueBooks) {
			sendMail(ch);
		}
	}
	
	private void sendMail(CheckOut chechOut) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("no-reply@bibliotheca.com");
		message.setTo(chechOut.getBorrower().getEmail());
		
		message.setSubject("Bibliotheca  Return Date Notification");
		message.setText(buildMessage(chechOut)); 
		
		mailSender.send(message);
	}
	
	private String buildMessage(CheckOut checkOut) {
		StringBuilder message = new StringBuilder();
		message.append("Bibliotheca Library\n");
		message.append("Dear ").append(checkOut.getBorrower().getFirstName()).append(",\n");
		message.append("The due date for returning the books you checked out is as follows, "
				+ "so please return them in advance so that they are not overdue\n");
		message.append("Title:    ").append(checkOut.getBookItem().getBook().getTitle()).append("\n");
		message.append("Borrow date:    ").append(checkOut.getCheckOutDate()).append("\n");
		message.append("Due date:    ").append(checkOut.getDueDate()).append("\n");
	
		return message.toString();
	}	
	
}
