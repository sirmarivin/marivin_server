package finalproject;


import com.sun.media.jai.codec.SeekableStream;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.commons.io.FileUtils;


public class PhotoHandler
{   
    //brings in the ability to take a picture
    public static void camera(String fileName) throws Exception
    {
        try{
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File(fileName));
        }
        catch(Exception e){}
    }
    //actually takes the picture
    public static void takePicture(String fileName){
        try {
            camera(fileName);
        } catch (Exception ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //crops the photo down to proper size
    public static void crop(String fileName, int down) throws IOException{
        int x = 0;
        int y = 71 + down;
        int w = 1583;
        int h = 799 - down;

        String path = fileName;
        File f = new File(path);
        
        BufferedImage image = ImageIO.read(new File(path));
        BufferedImage out = image.getSubimage(x, y, w, h);

        ImageIO.write(out, "jpg", new File(path));
        
    }
    //gathers all the footage from a website
    public static void gatherPage() throws IOException{
        (new File(Utilities.pictureFilePath+ReadingEmail.fileReady)).mkdir();
        JavascriptExecutor jse = (JavascriptExecutor)MasterClass.driver;       
       
        int pageLength = Integer.parseInt(String.valueOf(jse.executeScript( //gathers the length of the page using                                                                            
                "return Math.max(document.body.scrollHeight, "              //some gypsie javascript jazz
                + "document.body.offsetHeight,"
                + "document.documentElement.clientHeight, "
                + "document.documentElement.scrollHeight,"
                + "document.documentElement.offsetHeight)")));
        
        int fullPageLengths = pageLength / 799;
        int remainingPageLength = pageLength % 799;
        
        for(int i = 0; i < fullPageLengths && i < 6; i++){
            String whereTo = Utilities.pictureFilePath+ReadingEmail.fileReady+"\\"+i+".jpg";
            takePicture(whereTo);
            crop(whereTo, 0);
            jse.executeScript("window.scrollBy(0, 799)", "");
            Utilities.waiter(200);
        }
        
        //grabs the remaining bit at the bottom of the screen
        String finalBit = Utilities.pictureFilePath+ReadingEmail.fileReady+"\\"+(fullPageLengths + 1)+".jpg";
        takePicture(finalBit);
        crop(finalBit, 799 - remainingPageLength);
        
    }
    //combines all the photos from gathersPage into a single picture
    public static void combiner() throws IOException {
        
        // creating a BufferedImage to lay the groundwork for starting the
        // building of the picture.
        BufferedImage baseImage = new BufferedImage(1583, 799, BufferedImage.TYPE_INT_RGB);
        // getting the picture ready to be drawn on
        Graphics g = baseImage.getGraphics();
            // draws the picture onto the baseImage
        // "0.jpg" is the first picture in the list of pictures for a webpage
        g.drawImage(ImageIO.read(new File(Utilities.pictureFilePath + ReadingEmail.fileReady + "\\" + "0.jpg")), 0, 0, null);
        // saves the image to the final location
        ImageIO.write(baseImage, "JPG", new File(Utilities.pictureFilePath + ReadingEmail.fileReady + ".jpg"));
        
        File path = new File(Utilities.pictureFilePath+ReadingEmail.fileReady+"\\");

        for(int i = 1; i < 6; i++){
            if((new File(Utilities.pictureFilePath+ReadingEmail.fileReady+"\\"+i+".jpg").exists())){
                // load source images
                BufferedImage base = ImageIO.read(new File(Utilities.pictureFilePath+ReadingEmail.fileReady + ".jpg"));
                BufferedImage addOn = ImageIO.read(new File(path, i + ".jpg"));
                
                // create the new image, canvas size is the max. of both image sizes
                int height = base.getHeight();
                int width = 1583;
                
                // newest picture with newest dimensions
                BufferedImage newPicture = new BufferedImage(width, height + 799, BufferedImage.TYPE_INT_RGB);
                
                //create Graphics object and draw the new picture
                Graphics graphics = newPicture.getGraphics();
                graphics.drawImage(base, 0, 0, null);
                graphics.drawImage(addOn, 0, height, null);
                
                // Save as new image
                ImageIO.write(newPicture, "JPG", new File(Utilities.pictureFilePath+ReadingEmail.fileReady+".jpg"));
                
                
            }
            else{
                i += 10;
            }
        }
        //deletes directory
        FileUtils.deleteDirectory(new File(Utilities.pictureFilePath + ReadingEmail.fileReady));

    }
    
    //Not really sure what this does except prevent an error in the compress
    //method
    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }
    
    //lowers the resolution of the photo
    public static void compress() throws IOException {
        File original = new File(Utilities.pictureFilePath + ReadingEmail.fileReady + ".jpg");
        //takes the original fileName and then subtract the end, adds "Temp.jpg"
        File temporary = new File(Utilities.pictureFilePath + ReadingEmail.fileReady + "Temp.jpg");
           

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                original));
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(temporary));

        SeekableStream s = SeekableStream.wrapInputStream(bis, true);

        RenderedOp image = JAI.create("stream", s);
        ((OpImage) image.getRendering()).setTileCache(null);

        RenderingHints qualityHints = new RenderingHints(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        RenderedOp resizedImage = JAI.create("SubsampleAverage", image, .9999,
                .9999, qualityHints);

        JAI.create("encode", resizedImage, bos, "JPEG", null);
        
        bis.close();
        bos.close();
        s.close();
        
        //copies the temporary file to original location and then deletes the
        //temp file
        
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(temporary);
            os = new FileOutputStream(original);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        
        temporary.delete();
        
                
    }
    
}
