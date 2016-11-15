package hu.unideb.inf.rft.neuban.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/index")
public class IndexController {

	private static final String INDEX_VIEW = "index";

	@GetMapping
	public String loadIndexView() {
		return INDEX_VIEW;
	}
}
