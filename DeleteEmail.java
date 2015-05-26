package distributor;

import java.util.Properties;
 
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
 
/**
 * This program demonstrates how to remove e-mail messages on a mail server
 * using JavaMail API.
 * @author www.codejava.net
 *
 */
public class DeleteEmail {
    
    public static int counter = 0;
 
    public static void delete() throws MessagingException {
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "sir.marivin@gmail.com";
        String password = "marv is a boss";
        
        Properties properties = new Properties();
 
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
 
        Session session = Session.getDefaultInstance(properties);
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);
 
            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();
 
            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                String address = InternetAddress.toString(message.getFrom());
                //System.out.println(address);
                if (address.contains(ForwardEmail.sender)) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
 
            }
 
            // expunges the folder to remove messages which are marked deleted
            boolean expunge = true;
            folderInbox.close(expunge);
 
            store.close();
            counter = 0;
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            if(counter < 4){
                System.out.println("Could not connect to the message store.");
                Googler.theSignInKiller();
                System.out.println("Attempted to save it.");
                delete();
            }
            else{
                System.out.println("Tried to save it but it still died.");
                ex.printStackTrace();
            }
        }
    }
}
