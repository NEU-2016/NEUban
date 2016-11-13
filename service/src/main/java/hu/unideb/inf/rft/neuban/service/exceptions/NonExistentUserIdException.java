package hu.unideb.inf.rft.neuban.service.exceptions;

public class NonExistentUserIdException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_ERROR_MESSAGE = "User not found with this id";

    public NonExistentUserIdException(Long id) {
        super(DEFAULT_ERROR_MESSAGE + ": " + id.toString());
    }
    
    public NonExistentUserIdException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
