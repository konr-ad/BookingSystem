package com.booking.bookingSystem.service;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

    private Session getEmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", System.getenv("EMAIL_USER"));
        props.put("mail.smtp.password",  System.getenv("EMAIL_PASSWORD"));

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
            }
        });
    }

    public void sendEmailWithQRCode(String to, String subject, String text, BufferedImage qrImage) throws Exception {
        Session session = getEmailSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("your-email@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Create a multipart message for attachment
        Multipart multipart = new MimeMultipart();

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);
        multipart.addBodyPart(messageBodyPart);

        // Add QR Code as an attachment
        messageBodyPart = new MimeBodyPart();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        DataSource source = new ByteArrayDataSource(baos.toByteArray(), "image/png");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("QRCode.png");
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }
}
