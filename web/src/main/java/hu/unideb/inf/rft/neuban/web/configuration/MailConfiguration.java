package hu.unideb.inf.rft.neuban.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    @Value("${mail.protocol}")
    private String mailProtocol;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private Integer port;

    @Value("${mail.support.username}")
    private String username;

    @Value("${mail.support.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setProtocol(mailProtocol);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        final Properties properties = new Properties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "false");
        
        return properties;
    }
}