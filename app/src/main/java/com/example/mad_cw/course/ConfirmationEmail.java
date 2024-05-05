package com.example.mad_cw.course;

import android.widget.Toast;

import com.example.mad_cw.BuildConfig;
import com.example.mad_cw.user.UserConfirmation;
import com.example.mad_cw.user.UserModel;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmationEmail {
    public void sendEmail(String course, String email) {
        try {
            String stringSenderEmail = BuildConfig.SENDER_EMAIL_ADDRESS;
            String stringReceiverEmail = email;
            String stringPasswordSenderEmail = BuildConfig.SENDER_EMAIL_PASSWORD;

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Registration Successful!");
            mimeMessage.setText("Congratulations!, \nYou have successfully registered for the course: "
                            + course + "\nThank you!");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
