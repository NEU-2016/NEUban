package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.web.exceptions.LoginFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

	private static final String LOGIN_VIEW = "login";

	@GetMapping
	public String loadLoginView() {
		return LOGIN_VIEW;
	}

	@GetMapping(path = "/error")
	public void loginError() throws LoginFailureException {
		throw new LoginFailureException();
	}
}
