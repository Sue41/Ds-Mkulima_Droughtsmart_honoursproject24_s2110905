package com.example.myapplication.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String EMAIL = "jumasumeiya88@gmail.com";
    private static final String PASSWORD = "wiwj qtwf rbfg uxqn";

    // Define an interface for the callback
    public interface EmailCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public static void sendEmail(final String toEmail, final String subject, final String activity, final String code, final String validity, final String businessName, final EmailCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    Session session = Session.getDefaultInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EMAIL, PASSWORD);
                        }
                    });

                    // HTML content
                    String emailBody = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>OTP Verification</title>\n" +
                            "    <style>\n" +
                            "        body {\n" +
                            "            font-family: Arial, sans-serif;\n" +
                            "            color: #333;\n" +
                            "            margin: 0;\n" +
                            "            padding: 0;\n" +
                            "            background-color: #f9f9f9;\n" +
                            "        }\n" +
                            "        .container {\n" +
                            "            max-width: 600px;\n" +
                            "            margin: 20px auto;\n" +
                            "            padding: 20px;\n" +
                            "            background-color: #ffffff;\n" +
                            "            border: 1px solid #ddd;\n" +
                            "            border-radius: 5px;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            font-size: 24px;\n" +
                            "            margin-bottom: 10px;\n" +
                            "        }\n" +
                            "        p {\n" +
                            "            font-size: 16px;\n" +
                            "            line-height: 1.5;\n" +
                            "            margin: 0;\n" +
                            "        }\n" +
                            "        .otp-code {\n" +
                            "            display: inline-block;\n" +
                            "            font-size: 24px;\n" +
                            "            font-weight: bold;\n" +
                            "            color: #007bff;\n" +
                            "            background-color: #e9f0ff;\n" +
                            "            padding: 10px;\n" +
                            "            border-radius: 5px;\n" +
                            "        }\n" +
                            "        .footer {\n" +
                            "            margin-top: 20px;\n" +
                            "            font-size: 14px;\n" +
                            "            color: #777;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div class=\"container\">\n" +
                            "        <h1>One-Time Password (OTP) for " + businessName + "</h1>\n" +
                            "        <p>Hello,</p>\n" +
                            "        <p>To complete your " + activity + " request, please use the following One-Time Password (OTP):</p>\n" +
                            "        <p class=\"otp-code\">" + code + "</p>\n" +
                            "        <p>This OTP is valid for " + validity + " minutes and can only be used once. If you did not request this OTP, please disregard this email. For security reasons, do not share your OTP with anyone.</p>\n" +
                            "        <p>If you encounter any issues or have questions, feel free to contact our support team.</p>\n" +
                            "        <p>Thank you for choosing " + businessName + "!</p>\n" +
                            "        <div class=\"footer\">\n" +
                            "            <p>Best regards,<br>The " + businessName + " Team</p>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>\n";

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EMAIL));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                    message.setSubject(subject);
                    message.setContent(emailBody, "text/html; charset=utf-8");

                    Transport.send(message);
                    // Call the success callback
                    if (callback != null) {
                        callback.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Call the failure callback
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }
        }).start();
    }
}
