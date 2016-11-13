package hu.unideb.inf.rft.neuban.service.exceptions;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NonExistentBoardIdException extends UsernameNotFoundException {
	private static final String DEFAULT_ERROR_MESSAGE = "Board not found with this id";

    public NonExistentBoardIdException(Long id) {
        super(DEFAULT_ERROR_MESSAGE + ": " + id.toString());
    }
    
    public NonExistentBoardIdException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
