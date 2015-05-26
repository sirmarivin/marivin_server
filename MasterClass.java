package finalproject;


import java.io.IOException;
import javax.mail.MessagingException;
import org.openqa.selenium.WebDriver;

public class MasterClass {
    
    
    public static WebDriver passenger = Googler.googleDriverHelper();
    public static WebDriver driver = Googler.firefoxDriverHelper();
    public static boolean truth = true;
    
    public static void main(String[] args) throws MessagingException, IOException{
        Utilities.initFileChecker();    //creates all Files if none exists
        while(truth){
            ReadingEmail.readIt();          //checks for emails
            if (ReadingEmail.isEmail){
                if (MemberHandler.isMember(ReadingEmail.fileReady)){//if there is an email from a member...  
                    FileHandler.mineEmail(Utilities.dataFilePath+ReadingEmail.fileReady+".txt"); //returns important jazz from the email
                    Decipherer.decipher(FileHandler.decipheredEmail);//deciphers the aforementioned important jazz and does google stuff
                }
                else if (MemberHandler.isCaptain(ReadingEmail.fileReady)){
                    Captain.speaking();
                }
                else {
                    if(Utilities.isNumeric(ReadingEmail.fileReady)){
                        SendMail.generalTextReturner("Sorry...", "...but it seems "
                                + "that you are not a member of Marivin. \n\n"
                                + "If you think this is a mistake or you wish to have "
                                + "Sir Marivin as a service, please contact your wireless provider "
                                + "for further information.");//lets the user know they are not a member
                    }
                    else{
                        SendMail.generalTextReturner("So sorry...", "Marivin is a texting service and does not do emaily things. Sorry.");
                    }
                }
            }
            FileHandler.moveToArchive();    //archives the message
        }
    }
}
