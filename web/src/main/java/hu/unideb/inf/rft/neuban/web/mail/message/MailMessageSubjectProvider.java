package hu.unideb.inf.rft.neuban.web.mail.message;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MailMessageSubjectProvider {

	public static final String SUBJECT_REGISTRATION = "NEUban - Registration";
	public static final String SUBJECT_ADD_USER_TO_BOARD = "NEUban - Added to board";
	public static final String SUBJECT_ADD_USER_TO_BOARD_CIRCULAR = "NEUban - User added to board";
	public static final String SUBJECT_REMOVE_USER_FROM_BOARD = "NEUban - Removed from board";
	public static final String SUBJECT_REMOVE_USER_FROM_BOARD_CIRCULAR = "NEUban - User removed from board";
	public static final String SUBJECT_ADD_USER_TO_CARD = "NEUban - Assigned to card";
	public static final String SUBJECT_REMOVE_USER_FROM_CARD = "NEUban - Removed from card";
	public static final String SUBJECT_CARD_MOVING = "NEUban - Card moved";
}
