package com.kony;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class MailClient {
	Session session = null;
	MailClient(){
		final String username = "piyush.mittal@kony.com";
        final String password = "*****";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            @Override
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
		
	}

    public static void main(String[]args) throws IOException {

     new MailClient().sendEventMail( "piyush.mittal@kony.com","Bugbash");
       
    }
    
    
	
	void sendEventMail(String email,String event_desc) throws IOException{
    	 try {

             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress("piyush.mittal@kony.com"));
             message.setRecipients(Message.RecipientType.TO,
                 InternetAddress.parse(email));//Enter the recipient mail address
             message.setSubject("Don't Reply: Welcome");
             message.setText("HI");
             String everything;
             InputStream in = this.getClass().getResourceAsStream("welcome.html");
             
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             try {
                 StringBuilder sb = new StringBuilder();
                 String line = br.readLine();

                 while (line != null) {
                     sb.append(line);
                    // sb.append(System.lineSeparator());
                     line = br.readLine();
                 }
                 everything = sb.toString().replace("ActivateAccount", event_desc);
             } finally {
                 br.close();
             }
             message.setContent(everything, "text/html; charset=utf-8");

             Transport.send(message);

             //System.out.println("Done");

         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
    }
}