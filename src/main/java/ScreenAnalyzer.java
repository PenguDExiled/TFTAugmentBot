import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ScreenAnalyzer {
    public static String getPixelColour() {
        try {
            Thread.sleep(1000); // 1 Sekunden pausieren
            Robot robot = new Robot();
            int x_rec = 500;
            int y_rec = 360;
            int wid = 70;
            int hei = 70;
            ScreenCapture.takeScreenshot(x_rec,y_rec,wid,hei, "color");
            BufferedImage image = ImageIO.read(new File( "color.bmp"));
            List<String> augmentList = new ArrayList<>();
            System.out.println("\nx :" + x_rec + " y :" +y_rec + " w :" +wid + " h :" +hei);
            for (int x = 0; x< wid; x++) {
                for (int y = 0; y < hei; y++) {
                    Color pixelColor = new Color(image.getRGB(x,y));
                    int r = pixelColor.getRed();
                    int g = pixelColor.getGreen();
                    int b = pixelColor.getBlue();

                    if (r >= 100 && r <= 113 && g >= 120 && g <= 133 && b >= 146 && b <= 159) {
                        augmentList.add("silver");
                        System.out.println("silver!");
                    } else if (r >= 209 && r <= 222 && g >= 161 && g <= 174 && b >= 14 && b <= 27) {
                        augmentList.add("gold");
                        System.out.println("gold!");
                    } else if (r >= 190 && r <= 239 && g >= 140 && g <= 179 && b >= 200 && b <= 254) {
                        augmentList.add("prismatic");
                        System.out.println("prismatic!");
                    }
                }
            }

            if (augmentList.size() == 0) { // Die Liste hat nur ein leeres Element
                augmentList.add("empty!");
                System.out.println("empty!");
            }

            // Finde den häufigsten String in der Liste und gib ihn zurück
            return Collections.max(augmentList, (a, b) -> Collections.frequency(augmentList, a) -
                    Collections.frequency(augmentList, b));

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred!";
        }
    }


}
