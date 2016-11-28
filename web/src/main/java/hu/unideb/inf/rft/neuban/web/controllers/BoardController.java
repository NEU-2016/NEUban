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

	private static final String BOARD_MODEL_OBJECT_NAME = "board";

	@Autowired
	private BoardService boardService;

	@GetMapping(path = "/{boardId}")
	public ModelAndView loadBoardView(@PathVariable final Long boardId) {
		final ModelAndView modelAndView = new ModelAndView(BOARD_VIEW);
		//TODO error page if boardId doesn't exist
		modelAndView.addObject(BOARD_MODEL_OBJECT_NAME, boardService.get(boardId).orElse(null));
		return modelAndView;
	}
}