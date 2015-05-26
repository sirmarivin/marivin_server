package distributor;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
    public static void generalTextReturner (String subject, String mainMessage) throws MessagingException {
        String host = "smtp.gmail.com";
        String Password = "marv is a boss";
        String from = "sir.marivin@gmail.com";
        String toAddress = ForwardEmail.sender;
        // Get system properties
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, null);

        
        //the following sets up the format of the text message including
        //options and picture
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(mainMessage);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        
        message.setContent(multipart);

        try {
            Transport tr = session.getTransport("smtps");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail Sent Successfully");
            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }
    public static void emergencyTextToMe (String subject, String mainMessage) throws MessagingException {
        String host = "smtp.gmail.com";
        String Password = "marv is a boss";
        String from = "sir.marivin@gmail.com";
        String toAddress = "5743121467@mms.att.net";
        // Get system properties
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, null);

        
        //the following sets up the format of the text message including
        //options and picture
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(mainMessage);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        
        message.setContent(multipart);

        try {
            Transport tr = session.getTransport("smtps");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail Sent Successfully");
            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }
}
