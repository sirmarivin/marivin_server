package finalproject;



import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.lang.String;
import java.util.ArrayList;
import javax.mail.MessagingException;

public class FileHandler{
  public static String decipheredEmail = ""; //had to make this variable
                                             //because it was not returning the
                                             //proper thing for whatever reason  
  //use to make all new files for goodMorning
  //and new stuff
  public static void makeFile(String fileName, String input){
    PrintWriter writer = null;
    try {
      //create a temporary file
      String timeLog = new SimpleDateFormat("MM/dd/yy HH:mm..").format(Calendar.getInstance().getTime());
      File logFile = new File(Utilities.dataFilePath + fileName+".txt");
      writer = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
      writer.write(timeLog + " " + input+"\n");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Close the writer regardless of what happens...
        writer.close();
      } catch (Exception e) {
      }
    }
  }
  
  //writes into an existing file
  public static void writeIntoFile(String fileName, String input){
    String timeLog = new SimpleDateFormat("MM/dd/yy HH:mm..").format(Calendar.getInstance().getTime());
    if(input.equals("")){
    }
    else{
      try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Utilities.dataFilePath+fileName+".txt", true)))) {
        out.print(timeLog+" "+input+"\n");
      }catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
    }
  }
  public static ArrayList<String> readOutOfFile(String fileName){
    String line = null;
    ArrayList<String> arr = new ArrayList<String>();
    try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                arr.add(line);
            }    

            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                   
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return arr;
  }
  public static void mineEmail(String s) throws MessagingException{
      String decider = "";
      ArrayList<String> arr = new ArrayList<String>();
      arr = readOutOfFile(s);
      boolean mailSent = false;
      for(int num = 0; num < arr.size(); num++){
          //determines how to mine the MMS based on the carrier
            if(arr.get(num).contains("Subject:")){
                if(arr.get(num).contains("mypixmessages")){
                    for(int a = 0; a < arr.size(); a++){
                        if(arr.get(a).contains("Content-Location: text_0.txt")){
                            decipheredEmail = (arr.get(a+2));
                        }
                    }
                }
                else if(arr.get(num).contains("mms.att.net")){
                    for(int i = 0; i < arr.size(); i++){
                        if(arr.get(i).contains("<td>")){
                            decipheredEmail = arr.get(i+1);
                        }
                    }
                }
                else{
                    SendMail.generalTextReturner("Sorry...", "I was unable to "
                            + "process your message. Please contact your wireless "
                            + "provider for more information.");
                }
                num += arr.size();
                mailSent = true;
            }
        }
        if(!mailSent){
            SendMail.generalTextReturner("Sorry...", "I was unable to process "
                    + "your request. Please contact your wireless provider "
                    + "for more information.");
        }
      //removes spaces in both front and back of the message
      decipheredEmail = Utilities.justWords(decipheredEmail);
  }
  //moves the file after it has had the important stuff extracted
  public static void moveToArchive(){
      File myfile = new File(Utilities.dataFilePath + ReadingEmail.fileReady + ".txt");
      if(myfile.exists()){
          myfile.renameTo(new File(Utilities.archiveFilePath + myfile.getName()));
      }
  }
  public static void deleteFile(){
      File file = new File(Utilities.dataFilePath + ReadingEmail.fileReady + ".txt");
    	try{
            file.delete();
        }
        catch(Exception e){
            System.out.println("Dang");
        }
  }
  public static void makeGoogleLog(String phoneNumber, String input){
    PrintWriter writer = null;
    try {
      //create a temporary file
      String timeLog = new SimpleDateFormat("MM/dd/yy HH:mm..").format(Calendar.getInstance().getTime());
      File logFile = new File(Utilities.googleLogFilePath + phoneNumber +".txt");
      writer = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
      writer.write(timeLog + "\n" + input +"\n");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Close the writer regardless of what happens...
        writer.close();
      } catch (Exception e) {
      }
    }
  }
}