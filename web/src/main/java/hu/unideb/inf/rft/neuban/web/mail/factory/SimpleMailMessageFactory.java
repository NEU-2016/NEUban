package hu.unideb.inf.rft.neuban.web.mail.factory;


import hu.unideb.inf.rft.neuban.web.mail.message.MessageCategory;
import hu.unideb.inf.rft.neuban.web.mail.message.SimpleMailMessageHelper;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageSubjectProvider.SUBJECT_REGISTRATION;
import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageTextProvider.TEXT_REGISTRATION;
import static hu.unideb.inf.rft.neuban.web.mail.message.MessageCategory.CATEGORY_REGISTRATION;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class SimpleMailMessageFactory {

    public static SimpleMailMessage create(final MessageCategory messageCategory, final String mailAddress) {
        return createSimpleMailMessage(messageCategory, mailAddress);
    }

    public static SimpleMailMessage create(final MessageCategory messageCategory, final String[] mailAddresses) {
        return createSimpleMailMessage(messageCategory, mailAddresses);
    }

    private static SimpleMailMessage createSimpleMailMessage(final MessageCategory messageCategory, final String... mailAddresses) {
        if (messageCategory.equals(CATEGORY_REGISTRATION)) {
            return SimpleMailMessageHelper.builder()
                    .to(mailAddresses)
                    .subject(SUBJECT_REGISTRATION)
                    .text(TEXT_REGISTRATION)
                    .build();
        }

        return null;
    }

}
