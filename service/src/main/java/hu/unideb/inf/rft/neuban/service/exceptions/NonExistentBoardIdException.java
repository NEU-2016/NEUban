package hu.unideb.inf.rft.neuban.service.exceptions;

public class NonExistentBoardIdException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_ERROR_MESSAGE = "Board not found with this id";

    public NonExistentBoardIdException(Long id) {
        super(DEFAULT_ERROR_MESSAGE + ": " + id.toString());
    }
    
    public NonExistentBoardIdException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
