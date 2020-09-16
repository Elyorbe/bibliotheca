package tech.ibrokhimov.bibliotheca.web.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.Role;
import tech.ibrokhimov.bibliotheca.model.User;
import tech.ibrokhimov.bibliotheca.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private UserService userService;
	
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userService.findByUsername(username);
		if(!optionalUser.isPresent())
			throw new UsernameNotFoundException(String.format("Username <%s> not found.", username));
		
		User user = optionalUser.get();
		
		List<String> roles = new ArrayList<>();
		for(Role role : user.getRoles())
			roles.add(role.getRoleName());
		
		UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
		userBuilder.password(user.getPassword());
		userBuilder.roles(roles.toArray(new String[0]));
		return userBuilder.build();
	}

	
}
