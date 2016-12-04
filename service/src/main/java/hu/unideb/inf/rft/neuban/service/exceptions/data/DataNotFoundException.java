package hu.unideb.inf.rft.neuban.service.exceptions.data;

public abstract class DataNotFoundException extends Exception {

    private static final long serialVersionUID = 439458368360845820L;

    public DataNotFoundException(final String message) {
        super(message);
    }
}
