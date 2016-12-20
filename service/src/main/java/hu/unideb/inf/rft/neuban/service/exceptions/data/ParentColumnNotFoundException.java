package hu.unideb.inf.rft.neuban.service.exceptions.data;

public class ParentColumnNotFoundException extends DataNotFoundException {

	private static final long serialVersionUID = 7527251559474624086L;

	private static final String DEFAULT_ERROR_MESSAGE = "Parent column not found";

	public ParentColumnNotFoundException() {
		super(DEFAULT_ERROR_MESSAGE);
	}
}
