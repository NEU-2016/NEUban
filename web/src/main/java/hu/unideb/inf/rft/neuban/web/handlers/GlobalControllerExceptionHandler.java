package hu.unideb.inf.rft.neuban.web.handlers;

import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.web.exceptions.InvalidRegistrationException;
import hu.unideb.inf.rft.neuban.web.exceptions.LoginFailureException;
import hu.unideb.inf.rft.neuban.web.exceptions.NonExistentPrincipalUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final String ERROR_VIEW = "error";
	private static final String ERROR_MESSAGE_MODEL_OBJECT_NAME = "errorMessage";

	//TODO might break down for different scenarios
	@ExceptionHandler(value = {
			LoginFailureException.class,
			InvalidRegistrationException.class,
			NonExistentUserIdException.class,
			NonExistentPrincipalUserException.class,
			ConstraintViolationException.class,
			DataNotFoundException.class,
			ColumnAlreadyExistsException.class,
			CardAlreadyExistsException.class,
			CardNotFoundException.class
	})
	public ModelAndView defaultErrorHandler(final Exception e) {
		final ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);
		modelAndView.addObject(ERROR_MESSAGE_MODEL_OBJECT_NAME, e.getClass().getSimpleName() + ": " + e.getMessage());
		return modelAndView;
	}
}
