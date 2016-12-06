package hu.unideb.inf.rft.neuban.service.exceptions.data;


public class UserNotFoundException extends DataNotFoundException {

    private static final String DEFAULT_ERROR_MESSAGE = "User not found: ";

    private static final long serialVersionUID = -3484388564326897694L;

    public UserNotFoundException(final String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
