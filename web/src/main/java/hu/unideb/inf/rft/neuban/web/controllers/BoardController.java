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
	private static final String BOARD_NOT_FOUND_ERROR_MODEL_OBJECT_NAME = "boardNotFoundError";
	private static final String COLUMN_ALREADY_EXISTS_ERROR_MODEL_OBJECT_NAME = "columnAlreadyExistsError";
	private static final String COLUMN_NOT_FOUND_ERROR_MODEL_OBJECT_NAME = "columnNotFoundError";


	@Autowired
	private BoardService boardService;

	@Autowired
	private ColumnService columnService;

	@GetMapping
	public ModelAndView loadBoardView(@PathVariable final Long boardId) {
		final ModelAndView modelAndView = new ModelAndView(BOARD_VIEW);
		//TODO error page
		modelAndView.addObject(BOARD_MODEL_OBJECT_NAME, boardService.get(boardId).orElse(null));
		return modelAndView;
	}

	@PostMapping(path = "/createcolumn")
	public ModelAndView createColumn(@PathVariable final Long boardId, @RequestParam final String columnTitle) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		//TODO error page
		try {
			columnService.save(boardId, ColumnDto.builder().title(columnTitle).build());
		} catch (BoardNotFoundException e) {
			modelAndView.addObject(BOARD_NOT_FOUND_ERROR_MODEL_OBJECT_NAME, true);
		} catch (ColumnAlreadyExistsException e) {
			modelAndView.addObject(COLUMN_ALREADY_EXISTS_ERROR_MODEL_OBJECT_NAME, true);
		}
		return modelAndView;
	}

	@DeleteMapping(path = "/removecolumn/{columnId}")
	public ModelAndView removeColumn(@PathVariable final Long boardId, @PathVariable final Long columnId) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		//TODO error page
		try {
			columnService.remove(columnId);
		} catch (ColumnNotFoundException e) {
			modelAndView.addObject(COLUMN_NOT_FOUND_ERROR_MODEL_OBJECT_NAME, true);
		}
		return modelAndView;
	}
}