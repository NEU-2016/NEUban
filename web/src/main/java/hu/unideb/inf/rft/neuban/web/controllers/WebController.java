package hu.unideb.inf.rft.neuban.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class WebController {
	@RequestMapping(path = "/")
	public String defaultPath() {
		return "index.html";
	}

	@RequestMapping(path = "/neuban")
	public String neubanPath() {
		return "index.html";
	}
}
