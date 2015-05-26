package finalproject;

import java.io.IOException;
import javax.mail.MessagingException;

class Decipherer{
  public static void decipher(String s) throws MessagingException, IOException{
    //contains information concerning how to handle inputs
      //handles the event that the user sends back only a number to be retrieved
      if(s.equals("1")||
              s.equals("1.")||
              s.equals("2")||
              s.equals("2.")||
              s.equals("3")||
              s.equals("3.")||
              s.equals("4")||
              s.equals("4.")||
              s.toLowerCase().equals("more")||
              s.toLowerCase().equals("more.")){
          Googler.returner(s);
      }
      //when someone makes it explicit that they want to search something...
      else if(Utilities.firstWord(s.toLowerCase()).equals("search")){
          s = Utilities.readyForGoogle(s);
          Googler.initialGoogle(s);
      }
      //when someone searches for a phone number
      else if(Utilities.firstWord(s.toLowerCase()).equals("number")||
              Utilities.firstXWords(2,s.toLowerCase()).equals("phonenumber")||
              Utilities.firstWord(s.toLowerCase()).equals("phonenumber")){
          Googler.numberLookup(s);
      }
      //when someone searches for directions
      else if(Utilities.firstWord(s.toLowerCase()).contains("direction")){
          Googler.directioner(s);
      }
      //when someone searches for pictures
      else if(Utilities.firstWord(s.toLowerCase()).contains("picture")||
              Utilities.firstWord(s.toLowerCase()).contains("image")){
          Googler.pictureRetriever(s);
      }
      //when someone searches for weather
      else if(Utilities.firstWord(s.toLowerCase()).contains("weather")){
          Googler.weatherHandler(s);
      }
      else if(Utilities.firstWord(s.toLowerCase()).contains("translate")||
              Utilities.firstWord(s.toLowerCase()).contains("tranlate")){
          Googler.translator(s);
      }
      //when someone wants 'help'
      else if(Utilities.firstWord(s.toLowerCase()).contains("help")){
          SendMail.generalTextReturner("At your service...", ""
                  + "Send a text in one of the following formats:\n"
                  + "- Search (your search)\n"
                  + "- Number for (business) in (location)\n"
                  + "- Directions from (a location) to (another location)\n"
                  + "- Weather in (city, state)\n"
                  + "- Picture of (whatever you want)\n"
                  + "- Tranlate (word or phrase) to (language)\n"
                  + "\n\n"
                  + "");
      }
      //when no key words are used, the default is just a general search
      else{
          System.out.println("Possible error at "+ReadingEmail.fileReady + " who"
                  + " searched " + s +".");
          Googler.initialGoogle(s);
      }
    }
}
