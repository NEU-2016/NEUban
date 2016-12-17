package hu.unideb.inf.rft.neuban.web.mail;


import org.springframework.mail.SimpleMailMessage;

public interface NotificationService {

    void sendNotification(SimpleMailMessage simpleMailMessage);
}
