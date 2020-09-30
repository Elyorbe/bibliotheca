package tech.ibrokhimov.bibliotheca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BibliothecaServer {

	public static void main(String[] args) {
		SpringApplication.run(BibliothecaServer.class, args);
	}
	
	@Bean
	public static PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}
