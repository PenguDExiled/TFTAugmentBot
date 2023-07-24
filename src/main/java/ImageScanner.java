import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core.MinMaxLocResult;

import javax.swing.text.Position;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageScanner {
        public static double[] isOnScreen(String pathOrgImage, String pathCompareImage){
                double[] position = new double[2];
                // Bilder laden (Bild 1 und Bild 2)
                Mat image1 = Imgcodecs.imread(pathCompareImage);
                Mat image2 = Imgcodecs.imread(pathOrgImage);
                Mat result = new Mat();
                // Template-Matching durchführen
                try{
                        Imgproc.matchTemplate(image2, image1, result, Imgproc.TM_CCOEFF_NORMED);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        position[0] = -99999;
                        position[1] = -999;
                        return position;
                }


                // MinMaxLocResult abrufen, um die beste Übereinstimmung zu erhalten
                MinMaxLocResult mmr = Core.minMaxLoc(result);

                // MinMaxLocResult auswerten, um festzustellen, ob Bild 1 in Bild 2 enthalten ist
                double similarityThreshold = 0.9; // Schwellenwert für die Ähnlichkeit
                if (mmr.maxVal >= similarityThreshold) {
                        // Bild 1 ist in Bild 2 enthalten
                        System.out.println("Bild 1 ist in Bild 2 enthalten.");
                        System.out.println("Ähnlichkeitswert: " + mmr.maxVal);
                        System.out.println("Position: " + mmr.maxLoc.toString());
                        position[0] = mmr.maxLoc.x;
                        position[1] = mmr.maxLoc.y;
                        return position;
                } else {
                        // Bild 1 ist nicht in Bild 2 enthalten
                        System.out.println("Bild 1 ist nicht in Bild 2 enthalten.");
                        position[0] = -99999;
                        position[1] = -999;
                }
                return position;
        }
        public static double[] isOnScreen(String pathCompareImage) throws Exception {
                ScreenCapture.takeScreenshot("screenshot");
                double[] position = new double[2];
                // Bilder laden (Bild 1 und Bild 2)
                Mat image1 = Imgcodecs.imread(pathCompareImage);
                Mat image2 = Imgcodecs.imread("screenshot.bmp");

                // Template-Matching durchführen
                Mat result = new Mat();
                Imgproc.matchTemplate(image2, image1, result, Imgproc.TM_CCOEFF_NORMED);

                // MinMaxLocResult abrufen, um die beste Übereinstimmung zu erhalten
                MinMaxLocResult mmr = Core.minMaxLoc(result);

                // MinMaxLocResult auswerten, um festzustellen, ob Bild 1 in Bild 2 enthalten ist
                double similarityThreshold = 0.80; // Schwellenwert für die Ähnlichkeit
                if (mmr.maxVal >= similarityThreshold) {
                        // Bild 1 ist in Bild 2 enthalten
                        System.out.println("Bild 1 ist in Bild 2 enthalten.");
                        System.out.println("Ähnlichkeitswert: " + mmr.maxVal);
                        System.out.println("Position: " + mmr.maxLoc.toString());
                        position[0] = mmr.maxLoc.x;
                        position[1] = mmr.maxLoc.y;
                        return position;
                } else {
                        // Bild 1 ist nicht in Bild 2 enthalten
                        System.out.println("Bild 1 ist nicht in Bild 2 enthalten.");
                        Thread.sleep(2000);
                        position[0] = -99999;
                        position[1] = -999;
                }
                return position;
        }
}
