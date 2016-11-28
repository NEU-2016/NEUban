package hu.unideb.inf.rft.neuban.service.exceptions;


public class UserNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "User not found: ";

    public UserNotFoundException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
