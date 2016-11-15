package hu.unideb.inf.rft.neuban.service.exceptions;

public class NullFieldValueException extends IllegalStateException {

    private static final String DEFAULT_ERROR_MESSAGE = "Field cannot be null: null";

    public NullFieldValueException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
