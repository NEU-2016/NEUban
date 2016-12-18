package hu.unideb.inf.rft.neuban.web.mail.message;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MailMessageTextProvider {

	private static final String TEXT_SUFFIX = " Go check it out on NEUban!";

	public static final String TEXT_REGISTRATION = "Your registration to NEUban was successful!";
	public static final String TEXT_ADD_USER_TO_BOARD = "You have been added to a board!" + TEXT_SUFFIX;
	public static final String TEXT_ADD_USER_TO_BOARD_CIRCULAR = "A user has been added to a board!" + TEXT_SUFFIX;
	public static final String TEXT_REMOVE_USER_FROM_BOARD = "You have been removed from a board!";
	public static final String TEXT_REMOVE_USER_FROM_BOARD_CIRCULAR = "A user has been removed from a board!" + TEXT_SUFFIX;
	public static final String TEXT_ADD_USER_TO_CARD = "You have been assigned to a card!" + TEXT_SUFFIX;
	public static final String TEXT_REMOVE_USER_FROM_CARD = "You have been removed from a card!" + TEXT_SUFFIX;
	public static final String TEXT_CARD_MOVING = "One of your assigned card has moved to another column!" + TEXT_SUFFIX;
}
