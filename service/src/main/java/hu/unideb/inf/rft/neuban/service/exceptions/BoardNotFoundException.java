package hu.unideb.inf.rft.neuban.service.exceptions;


public class BoardNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Board not found: ";

    public BoardNotFoundException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
