package hu.unideb.inf.rft.neuban.web.exceptions;

public class NonExistentPrincipalUserException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Non-existent logged in user :";

	public NonExistentPrincipalUserException(final String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}
}
