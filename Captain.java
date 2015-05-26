package finalproject;

import java.io.IOException;
import javax.mail.MessagingException;




public class Captain {
    public static void speaking() throws MessagingException, IOException{
        FileHandler.mineEmail(Utilities.dataFilePath+ReadingEmail.fileReady+".txt");
        if(Utilities.firstXWords(2 , FileHandler.decipheredEmail.toLowerCase()).equals("listenup")){
            String thirdWord = Utilities.firstWord(FileHandler.decipheredEmail.substring(FileHandler.decipheredEmail.indexOf("up")+3));
            String fourthWord = Utilities.firstWord(FileHandler.decipheredEmail.substring(FileHandler.decipheredEmail.indexOf(thirdWord)+thirdWord.length()+1));
            if(!(thirdWord.equals("add")||thirdWord.equals("delete")||thirdWord.equals("member?")||
                    thirdWord.equals("kill"))){
                SendMail.generalTextReturner("Um...", "Sorry Cap, didn't quite catch that.");
            }
            else if(thirdWord.equals("add")){
                MemberHandler.addMember(fourthWord);
                SendMail.generalTextReturner("Sir,", "The member has been added.");
            }
            else if(thirdWord.equals("delete")){
                MemberHandler.deleteMember(fourthWord);
                SendMail.generalTextReturner("Sir,", "The member has been deleted.");
            }
            else if(thirdWord.equals("member?")){
                if(MemberHandler.isMember(fourthWord)){
                    SendMail.generalTextReturner("Sir,", "That user is a member.");
                }
                else{
                    SendMail.generalTextReturner("Sir,", "That user is not a member.");
                }
            }
            else if(thirdWord.equals("kill")){
                MasterClass.truth = false;
                MasterClass.driver.quit();
                MasterClass.passenger.quit();
                SendMail.generalTextReturner("Sir,", "she's dead.");
            }
        }
        else{
        Decipherer.decipher(FileHandler.decipheredEmail);
        }
    }
}
