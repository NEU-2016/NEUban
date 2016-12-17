package hu.unideb.inf.rft.neuban.web.mail.factory;


import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;

import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageSubjectProvider.SUBJECT_REGISTRATION;
import static hu.unideb.inf.rft.neuban.web.mail.message.MailMessageTextProvider.TEXT_REGISTRATION;
import static hu.unideb.inf.rft.neuban.web.mail.message.MessageCategory.CATEGORY_REGISTRATION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SimpleMailMessageFactoryTest {

    private static final String FIRST_MAIL_ADDRESS = "firstMail@gmail.com";
    private static final String SECOND_MAIL_ADDRESS = "secondMail@gmail.com";

    private final String[] mailAddresses = new String[]{FIRST_MAIL_ADDRESS, SECOND_MAIL_ADDRESS};

    @Test
    public void createShouldReturnSimpleMailMessageForRegistrationWhenParameterMessageCategoryIsRegistrationAndThereIsSingleRecipient() {
        // Given

        // When
        final SimpleMailMessage simpleMailMessage = SimpleMailMessageFactory.create(CATEGORY_REGISTRATION, FIRST_MAIL_ADDRESS);

        // Then
        assertThat(simpleMailMessage, notNullValue());

        assertThat(simpleMailMessage.getTo(), notNullValue());
        assertThat(simpleMailMessage.getTo().length, equalTo(1));
        assertThat(simpleMailMessage.getTo()[0], equalTo(FIRST_MAIL_ADDRESS));

        assertThat(simpleMailMessage.getSubject(), notNullValue());
        assertThat(simpleMailMessage.getSubject(), equalTo(SUBJECT_REGISTRATION));

        assertThat(simpleMailMessage.getText(), notNullValue());
        assertThat(simpleMailMessage.getText(), equalTo(TEXT_REGISTRATION));
    }

    @Test
    public void createShouldReturnSimpleMailMessageForRegistrationWhenParameterMessageCategoryAndThereAreTwoRecipients() {
        // Given

        // When
        final SimpleMailMessage simpleMailMessage = SimpleMailMessageFactory.create(CATEGORY_REGISTRATION, mailAddresses);

        // Then
        assertThat(simpleMailMessage, notNullValue());

        assertThat(simpleMailMessage.getTo(), notNullValue());
        assertThat(simpleMailMessage.getTo().length, equalTo(2));
        assertThat(simpleMailMessage.getTo()[0], equalTo(FIRST_MAIL_ADDRESS));
        assertThat(simpleMailMessage.getTo()[1], equalTo(SECOND_MAIL_ADDRESS));

        assertThat(simpleMailMessage.getSubject(), notNullValue());
        assertThat(simpleMailMessage.getSubject(), equalTo(SUBJECT_REGISTRATION));

        assertThat(simpleMailMessage.getText(), notNullValue());
        assertThat(simpleMailMessage.getText(), equalTo(TEXT_REGISTRATION));
    }
}
