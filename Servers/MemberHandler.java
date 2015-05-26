package finalproject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;


public class MemberHandler {
    //adds a member to the group
    public static void addMember(String number){
        
        String numberPath = Utilities.membersFilePath;
        for(int i = 0; i < number.length(); i++){
                numberPath += number.substring(i, i+1) + "\\";
            }
        
        new File(numberPath).mkdirs();
    }
    //checks the validity of a number
    public static boolean isMember(String number){
        
        boolean output = false;
        
        if(Utilities.isNumeric(number)){
            String numberPath = Utilities.membersFilePath;
            for(int i = 0; i < number.length(); i++){
                numberPath += number.substring(i, i+1) + "\\";
            }

            if (Files.exists(Paths.get(numberPath))) {
                output = true;
            }
        }
        return output;
    }
    //deletes a member from the group
    public static void deleteMember(String number){
        String numberPath = Utilities.membersFilePath;
        for (int i = 0; i < number.length(); i++) {
            numberPath += number.substring(i, i + 1) + "\\";
        }
        boolean gone = new File(numberPath).delete();
        if(gone){
            System.out.println("yizz");
        }
        else{
            System.out.println("noze");
        }

    }
    //checks if it is me
    public static boolean isCaptain(String number){
        return number.equals("5743121467");
    }
}
