package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {
	
	private static final String REDIRECT_URL_TO_NEUBAN_PATH = "redirect:/neuban";

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView redirectToNeubanPath() {
		return new ModelAndView(REDIRECT_URL_TO_NEUBAN_PATH);
	}
}
