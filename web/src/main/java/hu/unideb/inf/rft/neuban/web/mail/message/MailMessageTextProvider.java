package hu.unideb.inf.rft.neuban.web.mail.message;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MailMessageTextProvider {

    public static final String TEXT_REGISTRATION = "Welcome!\n" +
            "Your registration was successful!";
}
