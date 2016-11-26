package hu.unideb.inf.rft.neuban.service.exceptions;

public class ColumnAlreadyExistsException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Column already exists: ";

    public ColumnAlreadyExistsException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}