package com.captech.ioteam.ping;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    @Setter
    private JavaMailSender emailSender;

    private static final String FROM_EMAIL = "ioteam.captech.clt@gmail.com";
    private static final String USERNAME = "ioteam.captech.clt@gmail.com";
    private static final String PASSWORD = "iotbuildchallenge";

    private Map<String, String> numberCarrierMap = new HashMap<>();
    private Properties mailServerProps = new Properties();

    public EmailService() {
        // hardcoded phone number->carrier gateway address map
        numberCarrierMap.put("+18434259774", "8434259774@messaging.sprintpcs.com"); // chima
        numberCarrierMap.put("+17168662199", "7168662199@txt.att.net"); // Jay f
        numberCarrierMap.put("+18043064710", "8043064710@txt.att.net"); // Jack Cox
        numberCarrierMap.put("+18046473143", "8046473143@txt.att.net"); // Vinnie
        numberCarrierMap.put("+18043010397", "8043010397@txt.att.net"); // Brian

        //set mail Server Properties
        mailServerProps.put("mail.smtp.host", "smtp.gmail.com");
        mailServerProps.put("mail.smtp.port", "587");
        mailServerProps.put("mail.smtp.auth", "true");
        mailServerProps.put("mail.smtp.starttls.enable", "true");

    }

    public void sendEmailMessage(final String to, final String subject, final String body) throws MessagingException {
        sendSimpleEmail(to, subject, body);

    }

    public void sendSmsViaMailClient(final String toNumber, String alertLevel, String msg) throws MessagingException {
        sendSimpleEmail(Optional.ofNullable(numberCarrierMap.get(toNumber)).orElse("8434259774@messaging.sprintpcs.com"), alertLevel, msg);
    }

    /**
     * Sends a simple email message
     *
     * @param to
     * @param subject
     * @param body
     */
    private void sendSimpleEmail(final String to, final String subject, final String body) throws MessagingException {

        Session session = Session.getInstance(mailServerProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);

    }

}
