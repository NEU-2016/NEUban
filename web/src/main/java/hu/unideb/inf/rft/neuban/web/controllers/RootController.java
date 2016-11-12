package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {
	
	private static final String REDIRECT_URL_TO_NEUBAN_PATH = "redirect:/index";

	@RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
	public String redirectToNeubanPath() {
		return REDIRECT_URL_TO_NEUBAN_PATH;
	}
}
