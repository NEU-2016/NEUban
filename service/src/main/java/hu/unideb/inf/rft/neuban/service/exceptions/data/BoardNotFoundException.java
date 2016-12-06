package hu.unideb.inf.rft.neuban.service.exceptions.data;


public class BoardNotFoundException extends DataNotFoundException {

    private static final String DEFAULT_ERROR_MESSAGE = "Board not found: ";

    private static final long serialVersionUID = -8620280192775549564L;

    public BoardNotFoundException(final String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
