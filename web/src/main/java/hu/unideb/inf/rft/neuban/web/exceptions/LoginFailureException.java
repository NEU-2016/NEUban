package hu.unideb.inf.rft.neuban.web.exceptions;

public class LoginFailureException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Failed to log in!";

	private static final long serialVersionUID = 4850017579529050508L;

	public LoginFailureException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
