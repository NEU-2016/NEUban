package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/secure/boards")
public class BoardController {

	private static final String BOARDS_VIEW = "secure/boards";

	@GetMapping
	public String loadBoardsView() {
		return BOARDS_VIEW;
	}
}
