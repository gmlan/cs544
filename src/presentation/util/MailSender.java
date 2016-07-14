package presentation.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class MailSender {
 
    private static final String SENDERS_EMAIL = "eshopping.mum.edu@gmail.com";
    private static final String SENDERS_PWD = "eshopping@mum";
    
    private static Properties mailProps =  null;
    
    static{
    	mailProps = new Properties();
        
        // Set properties required to connect to Gmail's SMTP server
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
        mailProps.put("mail.smtp.port", "587");    
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");    
    } 
     
    public static  void send(String to, String subject, String content) {
        // Create a username-password authenticator to authenticate SMTP session
         Authenticator authenticator = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDERS_EMAIL, SENDERS_PWD);
            }
        };
        
        // Create the mail session
        Session session = Session.getDefaultInstance(mailProps, authenticator);
        try{
            // Create a default MimeMessage object.
            final MimeMessage message = new MimeMessage(session);
            
            // Set the sender's email address
            message.setFrom(new InternetAddress(SENDERS_EMAIL));
            
            // Set recipient's email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            // Set the subject of the email
            message.setSubject(subject);
            
            // Now set the actual message body of the email
            message.setText(content);
            
            // Send message
            Transport.send(message);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}