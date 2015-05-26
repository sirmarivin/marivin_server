package finalproject;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.mail.*;

public class ReadingEmail {
    
    // needs to be public for later access
    public static String returnAddress = "";
    public static String fileReady = "";
    public static Date timeIn = null;
    public static Boolean isEmail = false;
    
    public static void readIt() throws MessagingException, FileNotFoundException, IOException {
        //Properties props = new Properties();
        //props.setProperty("mail.store.protocol", "imaps");
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            // opens session, store, connects to the email account and checks
            // in the inbox for a message and opens it
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", Utilities.thisServerEmail, Utilities.thisServerPassword);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            // extracts the phone number from the message
            Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            returnAddress = msg.getSubject();            
            fileReady = Utilities.returnAddressToFileReady(returnAddress);
            timeIn = msg.getSentDate();
            
            // writes the contents of the message to a file which is labeled
            // based on the phone number of the sender
            String OUTPUT_FILE = Utilities.dataFilePath + fileReady + ".txt";
            OutputStream out = new FileOutputStream(OUTPUT_FILE);
            
            // we must write to an output stream because the message comes in
            // as a MimeMessage (whatever that is) and so we send it to a file
            // where we extracts the message from the html
            msg.writeTo(out);
            out.close(); //<----- The greatest act of the Lord thus far into the
                        //        project.
            //msg.setFlag(Flags.Flag.DELETED, true);
            //boolean expunge = true;
            //inbox.close(expunge);
            DeletingEmail.delete(); //deletes the email in order to avoid double
                                    //processing
            // lets the main statement know there is an email
            isEmail = true;
            //store.close();
            
            // in case the inbox is empty, we catch it and let it know it's okay.
        }catch (FileNotFoundException ex){ 
            DeletingEmail.delete();
            System.out.println("Some frigger tried to email you lol.");
            isEmail = true;
        }catch (Exception mex) {
            isEmail = false;
        }
    }
}