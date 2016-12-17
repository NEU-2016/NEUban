package hu.unideb.inf.rft.neuban.web.mail.impl;

import hu.unideb.inf.rft.neuban.web.mail.factory.SimpleMailMessageFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static hu.unideb.inf.rft.neuban.web.mail.message.MessageCategory.CATEGORY_REGISTRATION;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    private static final String MAIL_ADDRESS = "test@gmail.com";

    private final SimpleMailMessage simpleMailMessage = SimpleMailMessageFactory.create(CATEGORY_REGISTRATION, MAIL_ADDRESS);

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test(expected = IllegalArgumentException.class)
    public void sendNotificationShouldThrowIllegalArgumentExceptionWhenParameterSimpleMailMessageIsNull() {
        // Given

        // When
        this.notificationService.sendNotification(null);

        // Then
    }

    @Test(expected = MailException.class)
    public void sendNotificationShouldThrowMailExceptionWhenParameterSimpleMailMessageIsNotValid() {
        // Given
        doThrow(MailSendException.class).when(this.javaMailSender).send(simpleMailMessage);

        // When
        this.notificationService.sendNotification(simpleMailMessage);

        // Then
    }

    @Test
    public void sendNotificationShouldSendMailMessageWhenParameterSimpleMailMessageIsValid() {
        // Given
        doNothing().when(this.javaMailSender).send(simpleMailMessage);

        // When
        this.notificationService.sendNotification(simpleMailMessage);

        // Then
        then(this.javaMailSender).should().send(simpleMailMessage);
    }
}
