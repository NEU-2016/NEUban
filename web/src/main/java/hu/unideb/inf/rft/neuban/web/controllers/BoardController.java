package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.exceptions.NonExistentPrincipalUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/secure/board/{boardId}")
public class BoardController {

	private static final String BOARD_VIEW = "secure/board";
	private static final String REDIRECT_URL_TO_BOARD_VIEW = "redirect:/" + BOARD_VIEW;

	private static final String USERNAME_MODEL_OBJECT_NAME = "username";
	private static final String BOARD_MODEL_OBJECT_NAME = "board";

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private ColumnService columnService;

	@Autowired
	private CardService cardService;

	@GetMapping
	public ModelAndView loadBoardView(@PathVariable final Long boardId, final Principal principal) throws BoardNotFoundException, NonExistentPrincipalUserException {
		final ModelAndView modelAndView = new ModelAndView(BOARD_VIEW);
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElseThrow(() -> new NonExistentPrincipalUserException(principal.getName()));
		modelAndView.addObject(USERNAME_MODEL_OBJECT_NAME, currentUser.getUserName());
		final BoardDto boardModelObject = boardService.get(boardId).orElseThrow(() -> new BoardNotFoundException(String.valueOf(boardId)));
		modelAndView.addObject(BOARD_MODEL_OBJECT_NAME, boardModelObject);
		return modelAndView;
	}

	@PostMapping(path = "/createcolumn")
	public ModelAndView createColumn(@PathVariable final Long boardId, @RequestParam final String columnTitle) throws ColumnAlreadyExistsException, DataNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		columnService.save(boardId, ColumnDto.builder().title(columnTitle).build());
		return modelAndView;
	}

	@DeleteMapping(path = "/removecolumn/{columnId}")
	public ModelAndView removeColumn(@PathVariable final Long boardId, @PathVariable final Long columnId) throws ColumnNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		columnService.remove(columnId);
		return modelAndView;
	}

	@PostMapping(path = "/{columnId}/addcard")
	public ModelAndView addCard(@PathVariable final Long boardId, @PathVariable final Long columnId, @RequestParam final String cardTitle) throws CardAlreadyExistsException, DataNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		cardService.save(columnId, CardDto.builder().title(cardTitle).build());
		return modelAndView;
	}

	@DeleteMapping(path = "/removecard/{cardId}")
	public ModelAndView removeCard(@PathVariable final Long boardId, @PathVariable final Long cardId) throws CardNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		cardService.remove(cardId);
		return modelAndView;
	}
}