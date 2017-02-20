package com.kony;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailClient {

    public static void main(String[]args) throws IOException {

        final String username = "piyush.mittal@kony.com";
        final String password = "Krsna@29";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("piyush.mittal@kony.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("piyush.mittal@kony.com"));//Enter the recipient mail address
            message.setSubject("Welcome");
            message.setText("HI");
            String everything;
            BufferedReader br = new BufferedReader(new FileReader("welcome.html"));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                   // sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                everything = sb.toString().replace("ActivateAccount", "Piyush Mittal");
            } finally {
                br.close();
            }
            message.setContent(everything, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}