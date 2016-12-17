package hu.unideb.inf.rft.neuban.web.mail.impl;

import hu.unideb.inf.rft.neuban.web.mail.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(final SimpleMailMessage simpleMailMessage) {
        Assert.notNull(simpleMailMessage);

        this.javaMailSender.send(simpleMailMessage);
    }
}
