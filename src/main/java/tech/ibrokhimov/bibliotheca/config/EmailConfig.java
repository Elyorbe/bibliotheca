package tech.ibrokhimov.bibliotheca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailConfig {
	
	private EmailProperties emailProperties;
	public EmailConfig(EmailProperties emailProperties) {
		this.emailProperties = emailProperties;
	}
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailProperties.getHost());
		mailSender.setPort(emailProperties.getPort());
		mailSender.setUsername(emailProperties.getUsername());
		mailSender.setPassword(emailProperties.getPassword());
		
		return mailSender();
	}
}
