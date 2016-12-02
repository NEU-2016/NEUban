package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/secure/board/{boardId}")
public class BoardController {

	private static final String BOARD_VIEW = "secure/board";
	private static final String REDIRECT_URL_TO_BOARD_VIEW = "redirect:/" + BOARD_VIEW;

	private static final String BOARD_MODEL_OBJECT_NAME = "board";

	@Autowired
	private BoardService boardService;

	@Autowired
	private ColumnService columnService;

	@GetMapping
	public ModelAndView loadBoardView(@PathVariable final Long boardId) {
		final ModelAndView modelAndView = new ModelAndView(BOARD_VIEW);
		//TODO error page if boardId doesn't exist
		modelAndView.addObject(BOARD_MODEL_OBJECT_NAME, boardService.get(boardId).orElse(null));
		return modelAndView;
	}

	@PostMapping(path = "/addcolumn")
	public ModelAndView createBoard(@PathVariable final Long boardId, @RequestParam final String columnTitle) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		//TODO error page
		try {
			columnService.save(boardId, ColumnDto.builder().title(columnTitle).build());
		} catch (BoardNotFoundException e) {
			modelAndView.addObject("boardNotFoundError", true);
		} catch (ColumnAlreadyExistsException e) {
			modelAndView.addObject("columnAlreadyExistsError", true);
		}
		return modelAndView;
	}

	@PostMapping(path = "/removecolumn/{columnId}")
	public ModelAndView createBoard(@PathVariable final Long boardId, @PathVariable final Long columnId) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		//TODO error page
		try {
			columnService.remove(columnId);
		} catch (ColumnNotFoundException e) {
			modelAndView.addObject("columnNotFoundError", true);
		}
		return modelAndView;
	}
}