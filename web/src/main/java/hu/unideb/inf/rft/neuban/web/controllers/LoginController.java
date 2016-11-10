package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final String LOGIN_VIEW = "login";

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String loadLoginView() {
		return LOGIN_VIEW;
	}

	@RequestMapping(path = "/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return LOGIN_VIEW;
	}
}
