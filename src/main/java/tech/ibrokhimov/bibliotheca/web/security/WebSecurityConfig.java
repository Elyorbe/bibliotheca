package tech.ibrokhimov.bibliotheca.web.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
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
				.successHandler(authenticationSuccessHandler())
				.and()
			.logout()
				.logoutUrl(String.format("%s/perform_logout",Mappings.SIGN))
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.and()
			.csrf().disable();
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
				
				if(roles.contains("ROLE_ADMIN"))
					response.sendRedirect(String.format("%s/dashboard", Mappings.ADMIN));
				else
					response.sendRedirect(Mappings.HOME);
			}
		};
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
