package hu.unideb.inf.rft.neuban.service.exceptions;

import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;

public class CommentNotFoundException extends DataNotFoundException {

	private static final String DEFAULT_ERROR_MESSAGE = "Comment not found: ";

	public CommentNotFoundException(final String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}

}
