package tech.ibrokhimov.bibliotheca.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tech.ibrokhimov.bibliotheca.model.Role;
import tech.ibrokhimov.bibliotheca.model.User;
import tech.ibrokhimov.bibliotheca.service.UserService;

@Controller
@RequestMapping(Mappings.USER)
public class UserManageController {
	
	private UserService userService;

	public UserManageController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/create")
	public ModelAndView create() {
		
		final ModelAndView mav = new ModelAndView();
		mav.setViewName("user/create");
		
		return mav;
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> create(
			@RequestParam(value = "username") final String _username,
			@RequestParam(value = "email") final String _email,
			@RequestParam(value = "firstName") final String _firstName,
			@RequestParam(value = "lastName") final String _lastName,
			@RequestParam final String password){
		
		String username = _username.trim();
		String email = _email.trim();
		String firstName = _firstName.trim();
		String lastName = _lastName.trim();
		
		Optional<User> optUser = userService.findByUsername(username);
		if(optUser.isPresent())
			return ResponseEntity.ok(RestResponse.result(400, "User already exists"));
		
		User user = new User(username, email, firstName, lastName);
		user.setActive(true);
		user.setDeleted(false);
		user.setImmutable(false);
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(Role.getUserRole());
		user.setRoles(roleSet);
		
		userService.save(user, User.getAdminUser());
		userService.updatePassword(user, password, User.getAdminUser());
		
		return ResponseEntity.ok(RestResponse.ok());
	}
	
}
