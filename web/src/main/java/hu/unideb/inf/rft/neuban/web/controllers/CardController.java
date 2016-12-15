package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.exceptions.NonExistentPrincipalUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/secure/card/{cardId}")
public class CardController {

	private static final String CARD_VIEW = "secure/card";
	//private static final String REDIRECT_URL_TO_CARD_VIEW = "redirect:/" + CARD_VIEW;

	private static final String USERNAME_MODEL_OBJECT_NAME = "username";
	private static final String CARD_MODEL_OBJECT_NAME = "card";

	@Autowired
	private UserService userService;

	@Autowired
	private CardService cardService;

	@GetMapping
	public ModelAndView loadCardView(@PathVariable final Long cardId, final Principal principal) throws CardNotFoundException, NonExistentPrincipalUserException {
		final ModelAndView modelAndView = new ModelAndView(CARD_VIEW);
		final UserDto currentUser = userService.getByUserName(principal.getName()).orElseThrow(() -> new NonExistentPrincipalUserException(principal.getName()));
		modelAndView.addObject(USERNAME_MODEL_OBJECT_NAME, currentUser.getUserName());
		final CardDto cardModelObject = cardService.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));
		modelAndView.addObject(CARD_MODEL_OBJECT_NAME, cardModelObject);
		return modelAndView;
	}
}
