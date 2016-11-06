package hu.unideb.inf.rft.neuban.service.exceptions;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NonExistentUsernameException extends UsernameNotFoundException {
    private static final String DEFAULT_ERROR_MESSAGE = "Username not found: ";

    public NonExistentUsernameException(String message) {
        super(DEFAULT_ERROR_MESSAGE + message);
    }
}
