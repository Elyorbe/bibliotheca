package tech.ibrokhimov.bibliotheca.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import tech.ibrokhimov.bibliotheca.BibliothecaServer;
import tech.ibrokhimov.bibliotheca.controller.Mappings;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BibliothecaServer.getEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(Mappings.ADMIN, String.format("%s/**", Mappings.ADMIN)).hasRole("ADMIN")
				.antMatchers(Mappings.USER, String.format("%s/**", Mappings.USER)).hasRole("USER")
				.antMatchers(Mappings.HOME, String.format("%s/**", Mappings.HOME)).permitAll()
				.antMatchers(String.format("%s/in",Mappings.SIGN)).permitAll()
				.and()
			.formLogin()
				.loginPage(String.format("%s/in",Mappings.SIGN)).permitAll()
				.loginProcessingUrl(String.format("%s/perform_login",Mappings.SIGN))
				.defaultSuccessUrl(Mappings.HOME, true)
				.failureHandler(authenticationFailureHandler())
				.and()
			.logout()
				.logoutUrl(String.format("%s/perform_logout",Mappings.SIGN))
				.clearAuthentication(true)
				.and()
			.csrf().disable();
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				
				response.sendRedirect(String.format("%s/in",Mappings.SIGN));
			}
			
		};
	}
}
