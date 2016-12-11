package hu.unideb.inf.rft.neuban.web.exceptions;

public class NonExistentPrincipalUserException extends Exception {
	private static final String DEFAULT_ERROR_MESSAGE = "Non-existent logged in user :";

	private static final long serialVersionUID = 8233979467537949692L;

	public NonExistentPrincipalUserException(final String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}
}
