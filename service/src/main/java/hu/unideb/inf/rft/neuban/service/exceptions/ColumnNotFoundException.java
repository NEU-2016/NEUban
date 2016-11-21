package hu.unideb.inf.rft.neuban.service.exceptions;

public class ColumnNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Column not found: ";

    public ColumnNotFoundException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
