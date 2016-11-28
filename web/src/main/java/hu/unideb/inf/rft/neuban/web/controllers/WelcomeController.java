package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.handler.BoardHandler;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardHandler boardHandler;

	@GetMapping
	public ModelAndView loadWelcomeView(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WELCOME_VIEW);
		UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		modelAndView.addObject("boardList", boardService.getAllByUserId(currentUser.getId()));
		return modelAndView;
	}

	@PostMapping(path = "/create")
	public ModelAndView createBoard(Principal principal, @RequestParam String boardTitle) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(REDIRECT_URL_TO_WELCOME_VIEW);
		UserDto currentUser = userService.getByUserName(principal.getName()).orElse(null);
		try {
			boardHandler.createBoardByDefaultUserIdAndByTitle(currentUser.getId(), boardTitle);
		} catch (NonExistentUserIdException e) {
			//TODO kezeling it down or something
			modelAndView.addObject("nonExistentUserIdError", true);
		}
		return modelAndView;
	}


	//TODO implement when BoardService has actual method for removal
	/*
	@GetMapping(path = "/remove/{boardId}")
	public ModelAndView removeBoard(@PathVariable Long boardId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(REDIRECT_URL_TO_WELCOME_VIEW);
		boardService.
		return modelAndView;
	}
	*/
}
