package hu.unideb.inf.rft.neuban.web.exceptions;

public class LoginFailureException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Failed to log in!";

	public LoginFailureException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
