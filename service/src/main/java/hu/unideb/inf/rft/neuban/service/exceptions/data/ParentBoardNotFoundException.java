package hu.unideb.inf.rft.neuban.service.exceptions.data;

public class ParentBoardNotFoundException extends DataNotFoundException {
	
	private static final long serialVersionUID = -5802375019073455417L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "Parent board not found";

	public ParentBoardNotFoundException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
