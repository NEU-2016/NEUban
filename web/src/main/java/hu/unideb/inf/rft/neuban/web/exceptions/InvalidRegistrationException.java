package hu.unideb.inf.rft.neuban.web.exceptions;

public class InvalidRegistrationException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Invalid registrational data!";

	public InvalidRegistrationException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
