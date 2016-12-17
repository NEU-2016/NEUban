package hu.unideb.inf.rft.neuban.web.mail.message;

import lombok.Builder;
import org.springframework.mail.SimpleMailMessage;

@Builder
public class SimpleMailMessageHelper {

    public static class SimpleMailMessageHelperBuilder {

        private final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        public SimpleMailMessageHelperBuilder to(final String mailAddress) {
            simpleMailMessage.setTo(mailAddress);
            return this;
        }

        public SimpleMailMessageHelperBuilder to(final String[] mailAddresses) {
            simpleMailMessage.setTo(mailAddresses);
            return this;
        }

        public SimpleMailMessageHelperBuilder subject(final String subject) {
            simpleMailMessage.setSubject(subject);
            return this;
        }

        public SimpleMailMessageHelperBuilder text(final String text) {
            simpleMailMessage.setText(text);
            return this;
        }

        public SimpleMailMessage build() {
            return simpleMailMessage;
        }
    }
}
