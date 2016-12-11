package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.exceptions.NonExistentPrincipalUserException;
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

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@GetMapping
	public ModelAndView loadWelcomeView(final Principal principal) throws NonExistentPrincipalUserException {
		final ModelAndView modelAndView = new ModelAndView(WELCOME_VIEW);
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElseThrow(() -> new NonExistentPrincipalUserException(principal.getName()));
		modelAndView.addObject(BOARD_LIST_MODEL_OBJECT_NAME, boardService.getAllByUserId(currentUser.getId()));
		return modelAndView;
	}

	@PostMapping(path = "/createboard")
	public ModelAndView createBoard(final Principal principal, @RequestParam final String boardTitle) throws NonExistentPrincipalUserException, NonExistentUserIdException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_WELCOME_VIEW);
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElseThrow(() -> new NonExistentPrincipalUserException(principal.getName()));
		boardService.createBoard(currentUser.getId(), boardTitle);
		return modelAndView;
	}

	@DeleteMapping(path = "/removeboard/{boardId}")
	public ModelAndView removeBoard(@PathVariable final Long boardId) throws BoardNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_WELCOME_VIEW);
		boardService.remove(boardId);
		return modelAndView;
	}

}
