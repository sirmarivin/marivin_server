package distributor;

import javax.mail.MessagingException;
import static org.openqa.grid.common.SeleniumProtocol.WebDriver;
import org.openqa.selenium.WebDriver;

//in charge of distributing emails to the servers
public class Distributor {

    public static WebDriver passenger = Googler.googleDriverHelper();
    
    public static void main(String[] args) throws MessagingException {
        while(true){
            ForwardEmail.checkForMail();            
        }
    }
    
}
