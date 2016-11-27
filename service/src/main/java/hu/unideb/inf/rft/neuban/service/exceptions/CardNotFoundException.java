package hu.unideb.inf.rft.neuban.service.exceptions;

public class CardNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Card not found: ";

    public CardNotFoundException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}