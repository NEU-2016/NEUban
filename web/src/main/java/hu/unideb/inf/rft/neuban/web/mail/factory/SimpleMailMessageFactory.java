package hu.unideb.inf.rft.neuban.web.mail.factory;


import hu.unideb.inf.rft.neuban.web.mail.message.MailMessageCategory;
import hu.unideb.inf.rft.neuban.web.mail.message.SimpleMailMessageHelper;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageSubjectProvider.*;
import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageTextProvider.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class SimpleMailMessageFactory {

	public static SimpleMailMessage create(final MailMessageCategory mailMessageCategory, final String mailAddress) {
		return createSimpleMailMessage(mailMessageCategory, mailAddress);
	}

	public static SimpleMailMessage create(final MailMessageCategory mailMessageCategory, final String[] mailAddresses) {
		return createSimpleMailMessage(mailMessageCategory, mailAddresses);
	}

	private static SimpleMailMessage createSimpleMailMessage(final MailMessageCategory mailMessageCategory, final String... mailAddresses) {
		switch (mailMessageCategory) {
			case CATEGORY_REGISTRATION:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_REGISTRATION)
						.text(TEXT_REGISTRATION)
						.build();
			case CATEGORY_ADD_USER_TO_BOARD:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_ADD_USER_TO_BOARD)
						.text(TEXT_ADD_USER_TO_BOARD)
						.build();
			case CATEGORY_ADD_USER_TO_BOARD_CIRCULAR:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_ADD_USER_TO_BOARD_CIRCULAR)
						.text(TEXT_ADD_USER_TO_BOARD_CIRCULAR)
						.build();
			case CATEGORY_REMOVE_USER_FROM_BOARD:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_REMOVE_USER_FROM_BOARD)
						.text(TEXT_REMOVE_USER_FROM_BOARD)
						.build();
			case CATEGORY_REMOVE_USER_FROM_BOARD_CIRCULAR:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_REMOVE_USER_FROM_BOARD_CIRCULAR)
						.text(TEXT_REMOVE_USER_FROM_BOARD_CIRCULAR)
						.build();
			case CATEGORY_ADD_USER_TO_CARD:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_ADD_USER_TO_CARD)
						.text(TEXT_ADD_USER_TO_CARD)
						.build();
			case CATEGORY_REMOVE_USER_FROM_CARD:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_REMOVE_USER_FROM_CARD)
						.text(TEXT_REMOVE_USER_FROM_CARD)
						.build();
			case CATEGORY_CARD_MOVING:
				return SimpleMailMessageHelper.builder()
						.to(mailAddresses)
						.subject(SUBJECT_CARD_MOVING)
						.text(TEXT_CARD_MOVING)
						.build();
		}
		return null;
	}

}
