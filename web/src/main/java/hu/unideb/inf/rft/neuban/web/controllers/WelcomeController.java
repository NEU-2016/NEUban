package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/secure/welcome")
public class WelcomeController {

	private static final String WELCOME_VIEW = "secure/welcome";
	private static final String REDIRECT_URL_TO_WELCOME_VIEW = "redirect:/" + WELCOME_VIEW;

	private static final String BOARD_LIST_MODEL_OBJECT_NAME = "boardList";
	private static final String NON_EXISTENT_USER_ID_ERROR_MODEL_OBJECT_NAME = "nonExistentUserIdError";
	private static final String BOARD_NOT_FOUND_ERROR_MODEL_OBJECT_NAME = "boardNotFoundError";

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@GetMapping
	public ModelAndView loadWelcomeView(final Principal principal) {
		final ModelAndView modelAndView = new ModelAndView(WELCOME_VIEW);
		//TODO error page
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		modelAndView.addObject(BOARD_LIST_MODEL_OBJECT_NAME, boardService.getAllByUserId(currentUser.getId()));
		return modelAndView;
	}

	@PostMapping(path = "/createboard")
	public ModelAndView createBoard(final Principal principal, @RequestParam final String boardTitle) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_WELCOME_VIEW);
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		//TODO error page
		try {
			boardService.createBoard(currentUser.getId(), boardTitle);
		} catch (NonExistentUserIdException e) {
			modelAndView.addObject(NON_EXISTENT_USER_ID_ERROR_MODEL_OBJECT_NAME, true);
		}
		return modelAndView;
	}

	@DeleteMapping(path = "/removeboard/{boardId}")
	public ModelAndView removeBoard(@PathVariable final Long boardId) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_WELCOME_VIEW);
		//TODO error page
		try {
			boardService.remove(boardId);
		} catch (BoardNotFoundException e) {
			modelAndView.addObject(BOARD_NOT_FOUND_ERROR_MODEL_OBJECT_NAME, true);
		}
		return modelAndView;
	}

}
