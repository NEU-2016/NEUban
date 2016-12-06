package hu.unideb.inf.rft.neuban.service.exceptions;

public class CommentNotFoundException extends Exception {

	private static final String DEFAULT_ERROR_MESSAGE = "Comment not found: ";

	public CommentNotFoundException(String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}

}
