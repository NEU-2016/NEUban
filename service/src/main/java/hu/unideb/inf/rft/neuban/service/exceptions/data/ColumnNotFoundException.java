package hu.unideb.inf.rft.neuban.service.exceptions.data;

public class ColumnNotFoundException extends DataNotFoundException {

    private static final String DEFAULT_ERROR_MESSAGE = "Column not found: ";

    private static final long serialVersionUID = -3520290194866450483L;

    public ColumnNotFoundException(final String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
