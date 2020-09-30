package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import tech.ibrokhimov.bibliotheca.model.User;

public interface UserService {
	
	User save(User user, User actioner);
	User update(User user, User actioner);
	
	void updatePassword(User user, String password, User actioner);
	Optional<User> findByUsername(String username);
}
