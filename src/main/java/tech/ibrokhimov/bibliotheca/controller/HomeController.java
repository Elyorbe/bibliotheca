package tech.ibrokhimov.bibliotheca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(Mappings.HOME)
public class HomeController {
	
	@GetMapping
	public ModelAndView home() {
		
		final ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		return mav;
	}
}
