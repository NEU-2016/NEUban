package hu.unideb.inf.rft.neuban.web.controllers.rest;

import hu.unideb.inf.rft.neuban.web.mail.factory.SimpleMailMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.unideb.inf.rft.neuban.web.mail.message.MessageCategory.CATEGORY_REGISTRATION;

@RestController
public class EmailSenderSampleController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/mail")
    public String sendMailSampleMethod() {
        this.mailSender.send(SimpleMailMessageFactory.create(CATEGORY_REGISTRATION, "neuban3@gmail.com"));
        return "Mail sending was successful";
    }
}
