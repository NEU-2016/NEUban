package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/secure/board")
public class BoardController {

	private static final String BOARD_VIEW = "secure/board";

	@Autowired
	private BoardService boardService;

	@GetMapping(path = "/{boardId}")
	public ModelAndView loadBoardView(@PathVariable Long boardId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(BOARD_VIEW);
		modelAndView.addObject("board", boardService.get(boardId).orElse(null));
		return modelAndView;
	}
}