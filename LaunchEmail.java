package finalproject;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class LaunchEmail {
    /*
    //public static WebDriver driver = new FirefoxDriver(); 
    public static Properties properties = new Properties();
    public static Session session = Session.getDefaultInstance(properties);
    public static Store store;
    public static Transport transport;
    public static void launch(){
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "sir.marivin@gmail.com";
        String password = "marv is a boss";
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
        
        try{
            store = session.getStore("imap");
            store.connect(userName, password);
            transport = session.getTransport("smtps");
            transport.connect(host, userName, password);
        }
        catch(Exception e){
            System.out.println("Could not connect to Store");
        }
        
    }
    */
}
