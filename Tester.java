package finalproject;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static finalproject.FileHandler.readOutOfFile;
import static finalproject.ReadingEmail.fileReady;
import static finalproject.ReadingEmail.isEmail;
import static finalproject.ReadingEmail.returnAddress;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 

//the way to maximize the Firefox window is to use:

 
public class Tester {
    
    //public static WebDriver passenger = Googler.googleDriverHelper();
    //public static WebDriver testDriver = Googler.firefoxDriverHelper();
    
    public static void main(String[] args) throws MessagingException, IOException{;
        Date guy = new Date();
        System.out.println(guy);
    }
}
