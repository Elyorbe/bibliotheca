package tech.ibrokhimov.bibliotheca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(Mappings.SIGN)
public class LoginController {
	
	@GetMapping("/in")
	public ModelAndView in() {

		final ModelAndView mav = new ModelAndView();
		mav.setViewName("sign/in");
		
		return mav;
	}
}
