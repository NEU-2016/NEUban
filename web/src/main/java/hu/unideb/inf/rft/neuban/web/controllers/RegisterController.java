package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.exceptions.InvalidRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {

	private static final String REGISTER_VIEW = "register";
	private static final String INDEX_VIEW = "index";

	private static final String USER_DTO_MODEL_OBJECT_NAME = "userDto";

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;

	@GetMapping
	public ModelAndView loadRegisterView() {
		final ModelAndView modelAndView = new ModelAndView(REGISTER_VIEW);
		modelAndView.addObject(USER_DTO_MODEL_OBJECT_NAME, new UserDto());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView userRegister(@Valid @ModelAttribute final UserDto userDto, final BindingResult bindingResult) throws InvalidRegistrationException {
		this.userValidator.validate(userDto, bindingResult);
		final ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
		if (bindingResult.hasErrors()) {
			throw new InvalidRegistrationException();
		}
		userService.saveOrUpdate(userDto);
		return modelAndView;
	}
}
