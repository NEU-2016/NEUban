package hu.unideb.inf.rft.neuban.web.handlers;

import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final String ERROR_VIEW = "error";

	//TODO might break down for different scenarios
	@ExceptionHandler(value = {
			DataNotFoundException.class,
			ColumnAlreadyExistsException.class,
	})
	public ModelAndView defaultErrorHandler() {
		final ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);
		return modelAndView;
	}
}
