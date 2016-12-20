package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.*;
import hu.unideb.inf.rft.neuban.service.exceptions.data.*;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.exceptions.NonExistentPrincipalUserException;
import hu.unideb.inf.rft.neuban.web.mail.NotificationService;
import hu.unideb.inf.rft.neuban.web.mail.factory.SimpleMailMessageFactory;
import hu.unideb.inf.rft.neuban.web.mail.message.MailMessageCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private NotificationService notificationService;

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

	@GetMapping(path = "/movecardleft/{cardId}")
	public ModelAndView moveCardLeft(@PathVariable final Long boardId, @PathVariable final Long cardId) throws CardNotFoundException, ParentBoardNotFoundException, ParentColumnNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		cardService.moveCardToAnotherColumnByDirection(cardId, false);
		return modelAndView;
	}

	@GetMapping(path = "/movecardright/{cardId}")
	public ModelAndView moveCardRight(@PathVariable final Long boardId, @PathVariable final Long cardId) throws CardNotFoundException, ParentBoardNotFoundException, ParentColumnNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		cardService.moveCardToAnotherColumnByDirection(cardId, true);
		return modelAndView;
	}

	@PostMapping(path = "/adduser")
	public ModelAndView addUser(@PathVariable final Long boardId, @RequestParam final String username) throws DataNotFoundException, NonExistentUserIdException, NonExistentBoardIdException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		final UserDto userDto = userService.getByUserName(username).orElseThrow(() -> new UserNotFoundException(username));
		boardService.addUserToBoardByUserIdAndByBoardId(userDto.getId(), boardId);
		notificationService.sendNotification(SimpleMailMessageFactory.create(MailMessageCategory.CATEGORY_ADD_USER_TO_BOARD, userDto.getEmail()));
		notificationService.sendNotification(SimpleMailMessageFactory.create(MailMessageCategory.CATEGORY_ADD_USER_TO_BOARD_CIRCULAR, getEmailsFromUserList(userService.getAllByBoardId(boardId))));
		return modelAndView;
	}

	@DeleteMapping(path = "/removeuser")
	public ModelAndView removeUser(@PathVariable final Long boardId, @RequestParam final String username) throws DataNotFoundException, NonExistentBoardIdException, NonExistentUserIdException, RelationNotFoundException {
		final ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_TO_BOARD_VIEW + "/" + boardId);
		final UserDto userDto = userService.getByUserName(username).orElseThrow(() -> new UserNotFoundException(username));
		boardService.removeUserFromBoardByUserIdAndByBoardId(userDto.getId(), boardId);
		notificationService.sendNotification(SimpleMailMessageFactory.create(MailMessageCategory.CATEGORY_REMOVE_USER_FROM_BOARD, userDto.getEmail()));
		notificationService.sendNotification(SimpleMailMessageFactory.create(MailMessageCategory.CATEGORY_REMOVE_USER_FROM_BOARD_CIRCULAR, getEmailsFromUserList(userService.getAllByBoardId(boardId))));
		return modelAndView;
	}

	private String[] getEmailsFromUserList(final List<UserDto> userList) {
		return userList.stream().map(UserDto::getEmail).collect(Collectors.toList()).toArray(new String[userList.size()]);
	}
}