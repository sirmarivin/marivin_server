package finalproject;



import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Test_SendMail {

    public static void sendInitialGoogleMail(String options) throws MessagingException {
        String host = "smtp.gmail.com";
        String Password = "marv is a boss";
        String from = "sir.marivin@gmail.com";
        String toAddress = ReadingEmail.returnAddress;
        String filename = Utilities.pictureFilePath + ReadingEmail.fileReady +".jpg"; //CHANGED FOR TESTING
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
        message.setSubject("Here you go.");
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Not satisfied? Respond with the number of the"
                + " desired link below or respond 'more' to see more of the"
                + " current page.\n\n" + options);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);

        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
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
    
    //Handles when the user requests one of the options sent with the initial
    //text
    public static void sendSecondaryGoogleMail() throws MessagingException {
        String host = "smtp.gmail.com";
        String Password = "marv is a boss";
        String from = "sir.marivin@gmail.com";
        String toAddress = "5743121467@mms.att.net";
        String filename = Utilities.pictureFilePath + "5743121467_new.jpg";
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
        message.setSubject("Here you go.");
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("No picture? Tap on where the picture should be"
                + "and see if that helps."+"\n\n"+"Still not what you're looking for? Respond with"
                + " number representing an option from previous text or search"
                + " something else to start over.");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);

        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
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
    //Handles the situation where a user returns a number even though
    // his/her number is not stored in the Google search log.
    public static void sendNoFileReturner () throws MessagingException {
        String host = "smtp.gmail.com";
        String Password = "marv is a boss";
        String from = "sir.marivin@gmail.com";
        String toAddress = ReadingEmail.returnAddress;
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
        message.setSubject("Sorry...");
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("There is no memory of your earlier search. "
                + "Sorry about that. Please feel free to search something else.");

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

