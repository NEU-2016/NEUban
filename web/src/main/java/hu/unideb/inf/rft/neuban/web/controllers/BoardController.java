package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/secure/boards")
public class BoardController {

	private static final String BOARDS_VIEW = "secure/boards";

	@GetMapping
	public String loadBoardsView() {
		return BOARDS_VIEW;
	}

	@PostMapping(path = "/create")
	public ModelAndView createBoard(String username, String boardTitle) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(BOARDS_VIEW);
		//TODO implementing board creation
		return modelAndView;
	}
}
