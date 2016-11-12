package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	private static final String LOGIN_VIEW = "login";

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String loadLoginView() {
		return LOGIN_VIEW;
	}

	@RequestMapping(path = "/login-error")
	public ModelAndView loginError() {
		ModelAndView modelAndView = new ModelAndView(LOGIN_VIEW);
		modelAndView.addObject("loginError", true);
		return modelAndView;
	}
}
