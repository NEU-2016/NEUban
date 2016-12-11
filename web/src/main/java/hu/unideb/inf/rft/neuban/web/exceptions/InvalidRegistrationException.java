package hu.unideb.inf.rft.neuban.web.exceptions;

public class InvalidRegistrationException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Invalid registrational data!";

	private static final long serialVersionUID = -4630175001275797684L;

	public InvalidRegistrationException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
