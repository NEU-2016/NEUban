package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView loadWelcomeView(final Principal principal) {
		final ModelAndView modelAndView = new ModelAndView(WELCOME_VIEW);
		//TODO error page if user doesn't exist
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		modelAndView.addObject(BOARD_LIST_MODEL_OBJECT_NAME, boardService.getAllByUserId(currentUser.getId()));
		return modelAndView;
	}

	@PostMapping(path = "/create")
	public ModelAndView createBoard(final Principal principal, @RequestParam final String boardTitle) {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_WELCOME_VIEW);
		//TODO error page if user doesn't exist
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		try {
			boardService.createBoard(currentUser.getId(), boardTitle);
		} catch (NonExistentUserIdException e) {
			//TODO currentUsers' existence is already proven, if the application gets here, the problem is in the code
			modelAndView.addObject("nonExistentUserIdError", true);
		}
		return modelAndView;
	}

	@GetMapping(path = "/remove/{boardId}")
	public ModelAndView removeBoard(@PathVariable Long boardId) throws BoardNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(REDIRECT_URL_TO_WELCOME_VIEW);
		boardService.remove(boardId);
		return modelAndView;
	}

}
