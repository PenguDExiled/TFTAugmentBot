import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.presets.opencv_core;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextRecognition {

    /**
     * Makes a post request to the database.
     *
     * @return The most similar text from the list of possible augments.
     */
    public static List<String> imageToString() throws AWTException, IOException {
        System.out.println("reading augments...");
        int counter = 0;
        List<String> compare1 = new ArrayList<>();
        List<String> compare2 = new ArrayList<>();
        List<String> compare3 = new ArrayList<>();
        List<String> augmentList = new ArrayList<>();

        int x_rec = 430;
        int y_rec = 520;
        int width = 250;
        int height = 60;

        while (true) {
            try {
                Thread.sleep(500); // 0.5 Sekunden pausieren
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Robot robot = new Robot();

            // Erfasse den Bildausschnitt als Screenshot
            ScreenCapture.takeScreenshot(x_rec,y_rec,width,height, "text1");
            ScreenCapture.takeScreenshot((x_rec+400),y_rec,width,height, "text2");
            ScreenCapture.takeScreenshot((x_rec+800),y_rec,width,height, "text3");
            BufferedImage box1 = ImageIO.read(new File( "text1.bmp"));
            BufferedImage box2 = ImageIO.read(new File( "text2.bmp"));
            BufferedImage box3 = ImageIO.read(new File( "text3.bmp"));
            // Wandle das BufferedImage in ein OpenCV-Mat-Objekt um und führe die Graustufen-Konvertierung durch
            Mat matBox1 = toGrayMat(box1);
            Mat matBox2 = toGrayMat(box2);
            Mat matBox3 = toGrayMat(box3);

            // Führe die Texterkennung durch
            String augment1 = recognizeText(matBox1);
            String augment2 = recognizeText(matBox2);
            String augment3 = recognizeText(matBox3);
            System.out.println(" Augment 1: " + augment1 + " Augment 2: " + augment2 +" Augment 3: " + augment3);
            compare1.add(augment1);
            compare2.add(augment2);
            compare3.add(augment3);

            counter++;
            if (counter >= 20) {
                break;
            }
        }

        // Lies die Liste von möglichen Augmentnamen aus der Textdatei
        List<String> possibleAugments = readPossibleAugmentsFromFile("augments.txt");

        // Finde den String aus possibleAugments mit der höchsten Übereinstimmung zur erkannten Textliste
        String mostSimilarText1 = findMostSimilarText(compare1, possibleAugments);
        String mostSimilarText2 = findMostSimilarText(compare2, possibleAugments);
        String mostSimilarText3 = findMostSimilarText(compare3, possibleAugments);
        System.out.println(mostSimilarText1 + " " + mostSimilarText2 + " " + mostSimilarText3);
        augmentList.add(mostSimilarText1);
        augmentList.add(mostSimilarText2);
        augmentList.add(mostSimilarText3);
        return augmentList;
    }

    // Hilfsmethode zur Konvertierung eines BufferedImage in eine Graustufen-Mat
    private static Mat toGrayMat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        Mat mat = new Mat(byteArray);
        mat = org.bytedeco.opencv.global.opencv_imgcodecs.imdecode(mat, org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE);
        return mat;
    }

    // Hilfsmethode zur Texterkennung mithilfe von Tesseract
    private static String recognizeText(Mat mat) {
        try {
            // Tesseract-Instanz erstellen
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata/");
            tesseract.setLanguage("eng");

            opencv_imgproc.resize(mat, mat, new Size(2 * mat.size().width(), 2 * mat.size().height())); // Bildgröße erhöhen, um die Erkennungsqualität zu verbessern

            // Das Bild als temporäre Datei speichern
            File tempFile = File.createTempFile("temp", ".png");
            opencv_imgcodecs.imwrite(tempFile.getAbsolutePath(), mat);

            // Texterkennung durchführen
            String result = tesseract.doOCR(tempFile);

            // Temporäre Datei löschen
            tempFile.delete();

            return result.trim();

        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    // Hilfsmethode zum Lesen der möglichen Augmentnamen aus einer Textdatei
    private static List<String> readPossibleAugmentsFromFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    // Hilfsmethode zur Suche des Strings mit der höchsten Übereinstimmung in der Liste possibleAugments
    private static String findMostSimilarText(List<String> compare, List<String> possibleAugments) {
        int minDistance = Integer.MAX_VALUE;
        String mostSimilarText = "";

        for (String comparisonText : compare) {
            for (String possibleAugment : possibleAugments) {
                int distance = computeLevenshteinDistance(comparisonText, possibleAugment);
                if (distance < minDistance) {
                    minDistance = distance;
                    mostSimilarText = possibleAugment;
                }
            }
        }

        return mostSimilarText;
    }

    // Hilfsmethode zur Berechnung der Levenshtein-Distanz zwischen zwei Strings
    private static int computeLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
    // Hilfsklasse zur Rückgabe von Tupeln (Triplet)
    private static class Tuple<X, Y, Z> {
        public final X x;
        public final Y y;
        public final Z z;

        public Tuple(X x, Y y, Z z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}
