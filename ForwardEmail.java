package distributor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ForwardEmail {

    public static String sender = "";

    // Dictator decides which server an email will be sent to 
    public static int dictator = 1;
    public static int totalServers = 1;

    public static void checkForMail() throws MessagingException {
        Properties properties = new Properties();

        String host = "smtp.gmail.com";
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");
        properties.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(properties);
        try {
            // Get a Store object and connect to the current host
            Store store = session.getStore();
            store.connect("imap.gmail.com", "sir.marivin@gmail.com",
                    "marv is a boss");//change the user and password accordingly

            // Create a Folder object and open the folder
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();

            if (messages.length != 0) {
                for (int i = 0; i < messages.length; i++) {
                    Message message = messages[i];
                    String originalSender = InternetAddress.toString(message.getFrom());
                    //remembers the name to be deleted later
                    sender = originalSender;
                    Message forward = new MimeMessage(session);
                    // Fill in header
                    //determines which server to send the message to and is also set up
                    // such that I could have more than 10 servers
                    if (dictator < 10) {
                        forward.setRecipient(Message.RecipientType.TO,
                                (new InternetAddress("sir.marivin.server0" + dictator + "@gmail.com")));
                        dictator++;
                    } else {
                        forward.setRecipient(Message.RecipientType.TO,
                                (new InternetAddress("sir.marivin.server" + dictator + "@gmail.com")));
                        dictator++;
                    }
                    //resets the dictator when maximum number of servers has been reached
                    if (dictator > totalServers) {
                        dictator = 1;
                    }
                    //makes the subject be the previous number
                    forward.setSubject(originalSender);
                    // Create the message part
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    // Create a multipart message
                    Multipart multipart = new MimeMultipart();
                    // set content
                    messageBodyPart.setContent((Multipart) message.getContent());
                    // Add part to multi part
                    multipart.addBodyPart(messageBodyPart);
                    // Associate multi-part with message
                    forward.setContent(multipart);
                    forward.saveChanges();

                    // Send the message by authenticating the SMTP server
                    // Create a Transport instance and call the sendMessage
                    Transport t = session.getTransport("smtps");
                    try {
                        //connect to the smpt server using transport instance
                        t.connect(host, "sir.marivin@gmail.com", "marv is a boss");
                        t.sendMessage(forward, forward.getAllRecipients());
                    } finally {
                        t.close();
                    }

                    System.out.println("Message forwarded successfully.");

                    try {
                        message.setFlag(Flags.Flag.DELETED, true);
                    } catch (Exception e) {
                        System.out.println("A mi, no me gusta sus bocas");
                    }

                    
                }// end for
                //deletes all the flagged emails
                boolean expunge = true;
                folder.close(expunge);
                System.out.println("Deleted successfully.");
                // close the store
                store.close();
            }//end if

        } catch (FileNotFoundException ex) {
            DeleteEmail.delete();
            System.out.println("Some frigger tried to email you lol.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
