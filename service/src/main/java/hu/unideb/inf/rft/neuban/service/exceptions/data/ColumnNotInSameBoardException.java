package hu.unideb.inf.rft.neuban.service.exceptions.data;

public class ColumnNotInSameBoardException extends DataNotFoundException {

	private static final String DEFAULT_ERROR_MESSAGE = "Column not found under this board: ";

	private static final long serialVersionUID = 3263503275873549103L;

	public ColumnNotInSameBoardException(final String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}

}
