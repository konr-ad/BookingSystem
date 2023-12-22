package com.booking.bookingSystem.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    public void sendEmailWithQRCode(String to, String subject, String body, String qrCodeImage) throws MessagingException, IOException {
        // Konfiguracja właściwości serwera SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Dane uwierzytelniające konta Gmail
        final String myAccountEmail = "konradtestowy92@gmail.com";
        final String password = "lrcc bgvm jkzt ssxh ";


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Utwórz wiadomość e-mail
        Message message = prepareMessage(session, myAccountEmail, to, subject, body, qrCodeImage);

        // Wyślij wiadomość
        Transport.send(message);
        System.out.println("Wiadomość wysłana pomyślnie");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String to, String subject, String body, String qrCodeImage) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        // Utwórz treść e-maila z załącznikiem
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");

        MimeBodyPart qrCodeAttachment = new MimeBodyPart();
        qrCodeAttachment.attachFile(qrCodeImage); // plik z obrazem kodu QR

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(qrCodeAttachment);

        message.setContent(multipart);

        return message;
    }
}
