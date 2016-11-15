package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
	
	private static final String REDIRECT_URL_TO_NEUBAN_PATH = "redirect:/index";

	@GetMapping(path = {"", "/"})
	public String redirectToNeubanPath() {
		return REDIRECT_URL_TO_NEUBAN_PATH;
	}
}
