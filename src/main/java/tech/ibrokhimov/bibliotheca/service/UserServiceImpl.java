package tech.ibrokhimov.bibliotheca.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import tech.ibrokhimov.bibliotheca.model.User;
import tech.ibrokhimov.bibliotheca.model.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional
	public User save(User user, User actioner) {
		// TODO Needs to implemented
		return null;
	}

	@Override
	@Transactional
	public User update(User user, User actioner) {
		// TODO Needs to be implemented
		return null;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
