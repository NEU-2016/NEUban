package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping(path = "/neuban")
	public String neubanPath(Model model) {
		model.addAttribute("recipient", "World");
		return "index.html";
	}
}
