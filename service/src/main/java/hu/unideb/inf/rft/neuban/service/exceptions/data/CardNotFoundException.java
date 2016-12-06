package hu.unideb.inf.rft.neuban.service.exceptions.data;

public class CardNotFoundException extends DataNotFoundException {

    private static final String DEFAULT_ERROR_MESSAGE = "Card not found: ";

    private static final long serialVersionUID = 4880697507932281887L;

    public CardNotFoundException(final String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}