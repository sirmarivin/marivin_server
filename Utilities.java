package finalproject;



import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

class Utilities {
    
    
    //where all files are stored
    public static String masterFilePath = "C:\\Users\\Joshua\\Documents\\NetBeansProjects\\FinalProject\\";
    public static String dataFilePath = masterFilePath + "data\\";
    public static String archiveFilePath = masterFilePath + "archive\\";
    public static String pictureFilePath = masterFilePath + "pictures\\";
    public static String googleLogFilePath = masterFilePath + "googleLog\\";
    public static String membersFilePath = masterFilePath + "members\\";
    public static String thisServerEmail = "sir.marivin.server01@gmail.com";
    public static String thisServerPassword = "this server rocks";

    //just in case the isFile returns true, I must also obtain the file which
    // triggered true
    public static String fileToRemember; 

    //checks to see if String parameter is numeric
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    //returns did or have based on what the method didOrHave determines
    

    //checks to see if all files exists and creates them if not
    public static void initFileChecker() {
        if (!(new File(dataFilePath)).exists()) {
            new File(dataFilePath).mkdir();
        }
        if (!(new File(archiveFilePath)).exists()) {
            new File(archiveFilePath).mkdir();
        }
        if (!(new File(pictureFilePath)).exists()) {
            new File(pictureFilePath).mkdir();
        }
        if (!(new File(googleLogFilePath)).exists()) {
            new File(googleLogFilePath).mkdir();
        }
        if (!(new File(membersFilePath)).exists()) {
            new File(membersFilePath).mkdir();
        }
    }
    public static String[] stringSplitter(String s){
        String[] lines = s.split("\\r?\\n");
        return lines;
    }
    public static String longestString(String[] a){
        String longest = a[0];
        for(String s : a){
            if(s.length() > longest.length()){
                longest = s;
            }
        }
        return longest;
    }
    //gets the FileReady version of the returnAddress
    public static String returnAddressToFileReady(String s){
        return (s.substring(0,s.indexOf("@")));
    }
    //waits for a certain period of time
    public static void waiter(int i){
        try {
            Thread.sleep(i);           //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    //removes the "search" if such exists
    public static String readyForGoogle(String s){
        return s.substring(s.indexOf("earch")+6);
    }   
    
    //retrieves the desciption of the links and then returns only the good stuff
    public static String descriptionRetriever(int i){
        return Utilities.longestString(Utilities.stringSplitter(MasterClass.driver.findElement(By.xpath("//div[@id='main']"
            + "/div[@id='cnt']/div[@class='mw']/div[@id='rcnt']"
            + "/div[@class='col']/div[@id='center_col']"
            + "/div[@id='res']/div[@id='search']/div"
            + "/div[@id='ires']/ol[@id='rso']/div[@class='srg']"
            + "/li[@class='g'][" + i + "]/div[@class='rc']/div[@class='s']"
            + "")).getText()));
    }
    //retrieves the url's from the html
    public static String urlRetriever(int i){
        return firstWord((Utilities.stringSplitter(MasterClass.driver.findElement(By.xpath("//div[@id='main']"
            + "/div[@id='cnt']/div[@class='mw']/div[@id='rcnt']"
            + "/div[@class='col']/div[@id='center_col']"
            + "/div[@id='res']/div[@id='search']/div[@data-ved='0CBoQGg']"
            + "/div[@id='ires']/ol[@id='rso']/div[@class='srg']"
            + "/li[@class='g'][" + i + "]/div[@class='rc']/div[@class='s']"
            + "")).getText()))[0]);
    }
    //returns the first word
    public static String firstWord(String s){
        String output = "";
        for(int i = 0; i < s.length(); i++){
            if(!s.substring(i,i+1).equals(" ")){
                output += s.substring(i,i+1);
            }
            else{
                i += s.length();
            }
        }
        return output;
    }
    public static String firstXWords(int numberOfWords, String s){
        int counter = 0;
        String output = "";
        for(int i = 0; i < s.length() && counter < numberOfWords; i++){
            if(!s.substring(i,i+1).equals(" ")){
                output += s.substring(i,i+1);
            }
            else{
                counter++;
            }
        }
        return output;
    }
    //prints an array for testing purposes
    public static void printArray(String[] a){
        for(String s: a){
           System.out.println(s);
        }
    }
    //in case the user sends a return number with spaces about it
    public static int numberExtractor(String s){
        int output = 0;
        for(int i = 0; i < s.length(); i++){
            if(Utilities.isNumeric(s.substring(i,i+1))){
                output = Integer.parseInt(s.substring(i, i+1));
            }
        }        
        return output;
    }
    
    //removes excess length on the urls
    public static String urlReadier(String s){
        String output = "";
        int counter = 0;
        s = s.substring(7);
        for(int i = 0; i < s.length() && counter < 1; i++){
            if(!s.substring(i, i+1).equals("/")){
                output += s.substring(i, i+1);
            }
            else{
                counter++;
            }
        }
        return " Website:  " + output;
    }
    
    //removes spaces in front of and behind the important stuff
    public static String justWords(String s){
        if(!s.equals("")){
            for (int i = 0; true; i++) {
                if (s.substring(0, 1).equals(" ")) {
                    s = s.substring(1);
                } else {
                    break;
                }
            }
            for (int i = 0; true; i++) {
                if (s.substring(s.length() - 1).equals(" ")) {
                    s = s.substring(0, s.length() - 1);
                } else {
                    break;
                }
            }
        }
        return s;
    }
}

    

