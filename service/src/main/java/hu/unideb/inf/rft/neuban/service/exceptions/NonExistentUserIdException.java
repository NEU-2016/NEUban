package hu.unideb.inf.rft.neuban.service.exceptions;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NonExistentUserIdException extends UsernameNotFoundException {
    private static final String DEFAULT_ERROR_MESSAGE = "User not found with this id";

    public NonExistentUserIdException(Long id) {
        super(DEFAULT_ERROR_MESSAGE + ": " + id.toString());
    }
    
    public NonExistentUserIdException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
