/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Joshua
 */
public class Googler {
    public static void initialGoogle(String searchQuery) throws MessagingException, IOException{
            MasterClass.driver.get("http://www.google.com");
            WebElement element = MasterClass.driver.findElement(By.name("q"));
            element.sendKeys(searchQuery + "\n"); // send also a "\n"
            element.submit(); //hits enter

            MasterClass.driver.manage().window().maximize(); // maximizes window
            try{
            //waits until the Google page is fully loaded
            WebElement myDynamicElement = (new WebDriverWait(MasterClass.driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));

            //A list of descriptions for the user
            String desc_1 = Utilities.descriptionRetriever(1);
            String desc_2 = Utilities.descriptionRetriever(2);
            String desc_3 = Utilities.descriptionRetriever(3);
            String desc_4 = Utilities.descriptionRetriever(4);

    //makes a list of the links on the google search which we pull from into
            //variables
            List<WebElement> findElements = MasterClass.driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

            String url_1 = findElements.get(0).getAttribute("href");
            String url_2 = findElements.get(1).getAttribute("href");
            String url_3 = findElements.get(2).getAttribute("href");
            String url_4 = findElements.get(3).getAttribute("href");

            //brings up webpage
            MasterClass.driver.get(url_1);

            //takes a picture of dat dare screen den crops it
            String pictureLocation = Utilities.pictureFilePath + ReadingEmail.fileReady + ".jpg";
            PhotoHandler.takePicture(pictureLocation);
            PhotoHandler.crop(pictureLocation, 0);
            

            //makes a log of a number's last search in case it texts back
            FileHandler.makeGoogleLog(ReadingEmail.fileReady, desc_1 + "\n" + desc_2 + "\n" + desc_3 + "\n" + desc_4 + "\n"
                    + url_1 + "\n" + url_2 + "\n" + url_3 + "\n" + url_4 + "\n");

            //sets up the text so that people can respond
            String options = "2)" + Utilities.urlReadier(url_2) + "\n" + desc_2 + "\n\n" + "3)" + Utilities.urlReadier(url_3) + "\n" + desc_3 + "\n\n"
                    + "4)" + Utilities.urlReadier(url_4) + "\n" + desc_4;

            //send the email
            SendMail.sendInitialGoogleMail(options);
            }
            catch(Exception ex){
                SendMail.generalTextReturner("Sorry...", "Nothing matched your search.\n\nPlease try"
                        + " again with a different search or text 'help' for more options.");
            }
    }

    //when the user requests another page

    public static void returner(String s) throws MessagingException, IOException {
        int theNumber = 1;                              //if the return message is "more"
        if (Utilities.isNumeric(s)) {                     //then the page looked up will be
            theNumber = Utilities.numberExtractor(s);   //the first, otherwise it will be
        }                                               //the number requested
        //load the appropriate FilePath
        File f = new File(Utilities.googleLogFilePath + ReadingEmail.fileReady + ".txt");
        if (f.exists()) {
            ArrayList<String> arr = new ArrayList<String>();
            arr = FileHandler.readOutOfFile(Utilities.googleLogFilePath + ReadingEmail.fileReady + ".txt");
            //the way the GoogleLog is set up, it allows for the number sent in
            //as the next option to be added to 4 to find the line containing 
            //the desired url
            MasterClass.driver.get(arr.get(theNumber + 4));
            // maximizes window
            MasterClass.driver.manage().window().maximize();
            // gathers pictures of the pages and stores them to be accessed
            // directly thereafter
            PhotoHandler.gatherPage();
            PhotoHandler.combiner();
            PhotoHandler.compress();
            SendMail.sendSecondaryGoogleMail();
        }
        else{
            //alerts the user of their lack of earlier search
            SendMail.generalTextReturner("Sorry...","There is no memory of your earlier search. "
                + "Sorry about that. Please feel free to search something else.");
        }
    }
    
    public static void numberLookup(String searchQuery) throws MessagingException{
        MasterClass.driver.get("https://www.google.com/");
        WebElement element = MasterClass.driver.findElement(By.name("q"));
        element.sendKeys(searchQuery); // send also a "\n"
        element.submit(); //hits enter
        
        //waits until the Google page is fully loaded
        WebElement myDynamicElement = (new WebDriverWait(MasterClass.driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));

        
        String output = "";
        boolean isSideBarPresent = MasterClass.driver.findElements(By.xpath("//div[@id='rhs_block']/ol[1]/li[1]/div[1]/div[1]/div[1]/ol[1]/li[@data-md='7']")).size() > 0;
        boolean isTopTwoPresent = MasterClass.driver.findElements(By.xpath("//div[@class='intrlu'][1]/div/div[@class='g']/div[1]/h3")).size() > 0;
        if (isTopTwoPresent) {            
            //name of the place
            output += MasterClass.driver.findElement(By.xpath("//div[@class='intrlu'][1]/div/"
                + "div[@class='g']/div[1]/h3")).getText() + "\n\n";
            //location and number of the first place
            output += MasterClass.driver.findElement(By.xpath("//div[@class='intrlu'][1]/div/"
                + "div[@class='g']/div[3]")).getText() + "\n\n";
            //location and number of the second place
            output += MasterClass.driver.findElement(By.xpath("//div[@class='intrlu'][2]/div/"
                + "div[@class='g']/div[3]")).getText() + "\n\n";
        }
        
        else if(isSideBarPresent){
            //name of the place
            output += MasterClass.driver.findElement(By.xpath("//div[@id='rhs_block']/ol/li/div/div/div/ol/li[@data-md='16']/div/div")).getText() + "\n\n";            
            //number of the place
            output += "Phone:\n" + MasterClass.driver.findElement(By.xpath("//div[@id='search']/div/div[@id='ires']/ol/li/div/div/div/ol/li[@class='mod']/div/div/span")).getText();
        }
        
        else{
            output = "...nothing. Sorry about that.\n\n"
                    + "If you have not already, it may be helpful to include the"
                    + " city where your target is located.";
        }
        output += "\n\n"
                + "Not trying to look up a phone number but actually a "
                + "number of something? Start your text with the word "
                + "'search'.";
        SendMail.generalTextReturner("Marivin found...",output);
    }
    public static void directioner(String searchQuery) throws MessagingException{
        MasterClass.driver.get("https://www.google.com/");
        WebElement element = MasterClass.driver.findElement(By.name("q"));
        element.sendKeys(searchQuery + "\n"); // send also a "\n"
        element.submit(); //hits enter
        
        //waits until the Google page is fully loaded
        WebElement myDynamicElement = (new WebDriverWait(MasterClass.driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        
        String output = "";
        //finds out if the directions are right on the Google search page
        boolean isGooglePageDirections = MasterClass.driver.findElements(By.xpath("//div[@class='_fk _Ij single-line']/span")).size() > 0;
        if(isGooglePageDirections){
            //clicks on the button to expose elements
            WebElement button = MasterClass.driver.findElement(By.xpath("//div[@class='_Fx _NYd']/div"));
            JavascriptExecutor executor = (JavascriptExecutor)MasterClass.driver;
            executor.executeScript("arguments[0].click();", button);
            //states the total time and distance before the directions
            output += "Total time and distance:\n" + MasterClass.driver.findElement(By.xpath("//div[@class='_fk _Ij single-line']/span")).getText() + "\n\n";
            //inputs the directions
            output += "Start: " + MasterClass.driver.findElement(By.xpath("//div[@class='_hw card-section']/div/div/span")).getText()+"\n";
            int counter = 1;
            while(MasterClass.driver.findElements(By.xpath("//div[@class='numbered-step-start']/div[@class='_FPe']["+counter+"]")).size() > 0){
                output += counter + ". " + MasterClass.driver.findElement(By.xpath("//div[@class='numbered-step-start']/div["+counter+"]/div[2]")).getText()
                        + " for " + MasterClass.driver.findElement(By.xpath("//div[@class='numbered-step-start']/div["+counter+"]/div[3]")).getText() + "\n";
                counter++;
            }
            output += "End: " + MasterClass.driver.findElement(By.xpath("//div[@class='_hw card-section']/div[2]/div/span")).getText()+"\n";
            //send the directions
            SendMail.generalTextReturner("Marivin has your directions.", output);
            
        }
        else{
            SendMail.generalTextReturner("Sorry...", "Marivin could not find "
                    + "directions for you.\n\nIf you want, you can try again by "
                    + "rewording your search or text 'help' for more options.");
        }
    }
    public static ChromeDriver googleDriverHelper(){
        File file = new File("C:\\Users\\Joshua\\Documents\\NetBeansProjects\\HelperSoftware\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().setPosition(new Point(-2000, 0));
        return chromeDriver;
    }
    public static FirefoxDriver firefoxDriverHelper(){
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().window().maximize();
        return firefoxDriver;
    }
    public static void theSignInKiller() throws MessagingException{       
      
        try{
        MasterClass.passenger.get("https://www.gmail.com/");
        
        
        //Store the current window handle
        String winHandleBefore = MasterClass.passenger.getWindowHandle();
        
        JavascriptExecutor executor = (JavascriptExecutor)MasterClass.passenger;
        
        //waits until the EmailInput is fully loaded
        WebElement waitOnEmailInput = (new WebDriverWait(MasterClass.passenger, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("Email")));

        //Enters email
        WebElement emailInput = MasterClass.passenger.findElement(By.id("Email"));
        emailInput.sendKeys(Utilities.thisServerEmail);
        emailInput.submit();
        
        //waits until the EmailInput is fully loaded
        WebElement waitOnPasswordInput = (new WebDriverWait(MasterClass.passenger, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("Passwd")));

        //Enters email
        WebElement passwordInput = MasterClass.passenger.findElement(By.name("Passwd"));
        passwordInput.sendKeys(Utilities.thisServerPassword);
        passwordInput.submit();
        
        
        
        //waits until the detailsButton is fully loaded
        WebElement waitOnDetailsButton = (new WebDriverWait(MasterClass.passenger, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(":45")));

        //Perform the click operation that opens new window
        WebElement detailsButton = MasterClass.passenger.findElement(By.id(":45"));
        executor.executeScript("arguments[0].click();", detailsButton);
        
        //Switch to new window opened
        for (String winHandle : MasterClass.passenger.getWindowHandles()) {
            MasterClass.passenger.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        WebElement waitOnSignOutButton = (new WebDriverWait(MasterClass.passenger, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        WebElement signOutButton = MasterClass.passenger.findElement(By.xpath("//input[@type='submit']"));
        executor.executeScript("arguments[0].click();", signOutButton);
        
        //waits for the button to be pushed and for the page to reload
        
        WebElement waitForReload = (new WebDriverWait(MasterClass.passenger, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("change your password")));
        //Close the new window, if that window no more required
        MasterClass.passenger.close();
        
        Utilities.waiter(2000);

        //Switch back to original browser (first window)
        MasterClass.passenger.switchTo().window(winHandleBefore);
        //MasterClass.passenger.close();
        //MasterClass.passenger.quit();   
        }
        catch(TimeoutException ex){
            theSignInKiller();
        }
        catch(Exception ex){
            SendMail.emergencyTextToMe("Sir,", "Problem with the sign in killer."
                    + "Recommended that you look at it at once.");
            SendMail.generalTextReturner("Sorry,", "There has been an error. It will be handled promptly "
                    + "by one of our nerds. Thank you.");
        }
        
    }
    //handles the situation when user ask for the weather
    public static void weatherHandler(String searchQuery) throws MessagingException{
        MasterClass.driver.get("https://www.google.com/");
        WebElement element = MasterClass.driver.findElement(By.name("q"));
        element.sendKeys(searchQuery + "\n"); // send also a "\n"
        element.submit(); //hits enter
        
        //waits until the Google page is fully loaded
        WebElement myDynamicElement = (new WebDriverWait(MasterClass.driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        
        //checks if weather info is present
        boolean weatherInfoIsPresent = MasterClass.driver.findElements(By.xpath("//div[@class='vk_c card-section']")).size() > 0;
        String output = "";
        //reacts if weather info was present by gathering data
        if(weatherInfoIsPresent){
            //collects the area, the time, and the general status
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_c card-section']/div[1]")).getText() + "\n\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_c card-section']/div[2]")).getText() + "\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_c card-section']/div[3]")).getText() + "\n";
            
            //collects the temperature
            output += MasterClass.driver.findElement(By.xpath("//div[@id='wob_d']/div[1]/div/div/div[@class='vk_bk sol-tmp']/span[1]")).getText()
                    + MasterClass.driver.findElement(By.xpath("//div[@id='wob_d']/div[1]/div/div/div[@class='vk_gy wob-unit']/span[1]")).getText() + "\n";
            
            //collects precipitation, humidity and wind
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[1]")).getText() + "\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[2]")).getText() + "\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[3]")).getText() + "\n\n";
            
            //THE SAME STUFF BUT FOR THE NEXT DAY
            output += "Tomorrow: \n\n";
            
            //Perform the click operation that opens new window
            JavascriptExecutor executor = (JavascriptExecutor)MasterClass.driver;
            WebElement secondButton = MasterClass.driver.findElement(By.xpath("//div[@id='wob_dp']/div[2]"));
            executor.executeScript("arguments[0].click();", secondButton);
            
            //wait for page to load before going crasey
            Utilities.waiter(400);
            
            //collects the area, the time, and the general status
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_c card-section']/div[2]")).getText() + "\n";
            
            //collects the temperature
            output += MasterClass.driver.findElement(By.xpath("//div[@id='wob_d']/div[1]/div/div/div[@class='vk_bk sol-tmp']/span[1]")).getText()
                    + MasterClass.driver.findElement(By.xpath("//div[@id='wob_d']/div[1]/div/div/div[@class='vk_gy wob-unit']/span[1]")).getText() + "\n";
            
            //collects precipitation, humidity and wind
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[1]")).getText() + "\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[2]")).getText() + "\n";
            output += MasterClass.driver.findElement(By.xpath("//div[@class='vk_gy vk_sh wob-dtl']/div[3]")).getText() + "\n\n";
            
            SendMail.generalTextReturner("Sir Marivin with your weather,", output + "\n Thank you for using Sir Marivin.");
        }
        else{
            SendMail.generalTextReturner("So sorry,", "but there was no weather "
                    + "for the area you indicated. Please ask for the 'weather in "
                    + "(a certain area)' or a nearby location if your first location"
                    + " is unavailable.\n\n"
                    + "For more help, text 'help'!");
        }
        
    }
    //retrieves picture for the user
    public static void pictureRetriever(String searchQuery) throws MessagingException, IOException{
        MasterClass.driver.get("https://www.google.com/");
        WebElement element = MasterClass.driver.findElement(By.name("q"));
        element.sendKeys(searchQuery + "\n"); // send also a "\n"
        element.submit(); //hits enter
        
        //waits for Images link
        WebElement waitForImagesLink = (new WebDriverWait(MasterClass.driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Images")));
        
        //actual link we want to click on
        WebElement imagesLink = MasterClass.driver.findElement(By.linkText("Images"));
        //clicks it
        imagesLink.click();
        
        //brings in JavaScript executor and does stuff with it
        JavascriptExecutor jse = (JavascriptExecutor)MasterClass.driver;
        
        //waits for page to get settled
        WebElement waitForImages = (new WebDriverWait(MasterClass.driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("rg")));
        Utilities.waiter(2000);
        
        //checks if certain elements are on the screen
        boolean bigTopBarIsPresent = MasterClass.driver.findElements(By.xpath("//div[@id='ifbc']")).size() > 0;
        boolean largeShowingResultsForIsPresent = MasterClass.driver.findElements(By.xpath("//div[@class='sp_cnt card-section']/span")).size() > 0;
        boolean smallSearchInsteadForIsPresent = MasterClass.driver.findElements(By.xpath("//div[@class='sp_cnt card-section']/span[2]")).size() > 0;
        boolean didYouMeanIsPresent = MasterClass.driver.findElements(By.xpath("//div[@class='ssp card-section']/span")).size() > 0;
        boolean picturePresent = MasterClass.driver.findElements(By.xpath("//div[@class='rg_di rg_el']")).size() > 0;
        
        //then decides what to do based on which elements exist
        if(picturePresent){
            if(bigTopBarIsPresent){
                jse.executeScript("window.scrollBy(0, 300)", "");
            }
            else if(largeShowingResultsForIsPresent && smallSearchInsteadForIsPresent){
                jse.executeScript("window.scrollBy(0, 201)", "");
            }
            else if(didYouMeanIsPresent){
                jse.executeScript("window.scrollBy(0, 178)", "");
            }
            else{
                jse.executeScript("window.scrollBy(0, 119)", "");
            }
            String whereTo = Utilities.pictureFilePath + ReadingEmail.fileReady + ".jpg";
            PhotoHandler.takePicture(whereTo);
            PhotoHandler.crop(whereTo, 0);
            SendMail.sendImageSearchMail();
        }
        //or if elements exist
        else{
            SendMail.generalTextReturner("Sorry...", "but your picture search "
                    + "did not return any results. You can try a new picture search "
                    + "or text 'help' for a complete list of options.");
            }
    }
    public static void translator(String searchQuery) throws MessagingException{
        MasterClass.driver.get("https://www.google.com/");
        WebElement element = MasterClass.driver.findElement(By.name("q"));
        element.sendKeys(searchQuery + "\n"); // send also a "\n"
        element.submit(); //hits enter
        
        //waits for Images link
        WebElement waitForImagesLink = (new WebDriverWait(MasterClass.driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        
        //looks for translator
        boolean translatorPresent = MasterClass.driver.findElements(By.xpath("//div[@id='tw-source']")).size() > 0;
        
        //then decides what to do based on which elements exist
        if(translatorPresent){
            String output = "";
            //collects original language
            output += Utilities.firstWord(MasterClass.driver.findElement(By.xpath("//select[@class='tw-lang-selector vk_txt'][1]/option[@selected='1']")).getText()) + ":" + "\n";
            //collects original phrase
            output += MasterClass.driver.findElement(By.xpath("//pre[@id='tw-source-text']/span[1]")).getText() + "\n\n"; 
            //collects requested language
            output += Utilities.firstWord(MasterClass.driver.findElement(By.xpath("//select[@id='tw-tl']/option[@selected='1']")).getText()) + ":" + "\n";
            //collects requested phrase
            output += MasterClass.driver.findElement(By.xpath("//pre[@id='tw-target-text']/span[1]")).getText() + "\n\n"; 
            
            output += "Thank you for using Sir Marivin.";
            SendMail.generalTextReturner("Marivin has translated your request" , output);
        }
        //or if elements exist
        else{
            SendMail.generalTextReturner("Sorry...", "but no results were found "
                    + "for the translation you asked for. Try a different 'translate' "
                    + "search or text 'help' for more options.");
            }
    }
    
}