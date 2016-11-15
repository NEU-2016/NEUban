package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

	private static final String LOGIN_VIEW = "login";

	@GetMapping
	public String loadLoginView() {
		return LOGIN_VIEW;
	}

	@GetMapping(path = "/error")
	public ModelAndView loginError() {
		ModelAndView modelAndView = new ModelAndView(LOGIN_VIEW);
		modelAndView.addObject("loginError", true);
		return modelAndView;
	}
}
