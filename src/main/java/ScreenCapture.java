import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenCapture {
    public static void takeScreenshot(String file) throws AWTException, IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);

        File imageFile = new File(file+".bmp");
        ImageIO.write(capture, "bmp", imageFile );
    }
    public static void takeScreenshot(int x, int y, int width, int height, String file) throws AWTException, IOException {
        Rectangle region = new Rectangle(x, y, width, height);
        BufferedImage capture = new Robot().createScreenCapture(region);

        File imageFile = new File(file+".bmp");
        ImageIO.write(capture, "bmp", imageFile );
    }
}
