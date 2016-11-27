package hu.unideb.inf.rft.neuban.service.exceptions;


public class CardAlreadyExistsException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Card already exists: ";

    public CardAlreadyExistsException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}