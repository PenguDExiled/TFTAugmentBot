import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.bytedeco.opencv.presets.opencv_core;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TFTBot {
    static final String tftSet = '9.0';
    public static void main(String[] args) throws Exception {
        OpenCV.loadLocally();
        SwingUtilities.invokeLater(GUI::new);
    }
    public static void Run(String username) throws Exception {
        int counter = 0;
        double[] tmp;
        boolean isFindMatch = false;
        boolean isAccepted = false;
        while (true){
            if(!isFindMatch&&(tmp = ImageScanner.isOnScreen("images\\FindMatch.png"))[0] != -99999) {
                System.out.println("found");
                GameProgress.moveMouseAndClick((int)tmp[0], (int) tmp[1]);
                isFindMatch = true;
            }
            else {
                System.out.printf("cant find FindMatch");
            }
            if(!isAccepted&&isFindMatch && (tmp = ImageScanner.isOnScreen("images\\Accept.png"))[0] != -99999){
                System.out.println("found");
                GameProgress.moveMouseAndClick((int)tmp[0], (int) tmp[1]);
                isAccepted = true;
            }
            else {
                System.out.println("cant find Accept Button");
            }
            if(isAccepted){
                counter++;
                System.out.println("New Game! Game Number: " + counter);
                RunGame(username);
            }

        }
    }
    public static void RunGame(String username) throws Exception {
        System.out.println("game in progress...");
        String gameID = RandomStringGenerator.generateRandomString();
        List<String> augments;
        double[] tempPos;
        int stagecount = 1;
        String stage;
        String rarity;
        boolean is1_3 = false;
        boolean is2_1= false;
        boolean is2_5 = false;
        boolean is3_2 = false;
        boolean is3_5 = false;
        boolean is4_2 = false;
        boolean isForfeit = false;
        while (stagecount<4){

            if(!is1_3 && (tempPos = ImageScanner.isOnScreen("images\\1-3.png"))[0] != -99999){
                GameProgress.sellUnits();
                stage = "1-3";
                is1_3 = true;
            } else if (!is2_1 && (tempPos = ImageScanner.isOnScreen("images\\2-1.png"))[0] != -99999) {
                stage = "2-1";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2) + " " + rarity + " " + stage + " " + username + " " + gameID + " " + tftSet);
                Data.addData(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2), rarity,stage,username,gameID, tftSet);
                GameProgress.takeAugment();
                is2_1 = true;
            } else if (!is2_5 && (tempPos = ImageScanner.isOnScreen("images\\2-5.png"))[0] != -99999) {
                System.out.println("2-5");
                GameProgress.sellUnits();
                stage = "2-5";
                is2_5 = true;
            } else if (!is3_2 && (tempPos = ImageScanner.isOnScreen("images\\3-2.png"))[0] != -99999) {
                stage = "3-2";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.toString() + " " + rarity + " " + stage + " " + username + " " + gameID);
                Data.addData(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2), rarity,stage,username,gameID, tftSet);
                GameProgress.takeAugment();
                is3_2 = true;
            } else if (!is3_5 && (tempPos = ImageScanner.isOnScreen("images\\3-5.png"))[0] != -99999) {
                GameProgress.sellUnits();
                stage = "3-5";
                is3_5 = true;
            } else if (!is4_2 && (tempPos = ImageScanner.isOnScreen("images\\4-2.png"))[0] != -99999) {
                stage = "4-2";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.toString() + " " + rarity + " " + stage + " " + username + " " + gameID);
                Data.addData(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2), rarity,stage,username,gameID, tftSet);
                GameProgress.takeAugment();
                is4_2 = true;
            }
        }
        while (!isForfeit){
            if((tempPos = ImageScanner.isOnScreen("images\\4-3.png"))[0] != -99999){
                isForfeit = true;
                GameProgress.findImageAndClick("images\\forfeit.png");
                Thread.sleep(100);
                GameProgress.findImageAndClick("images\\ffbutton.png");
                Thread.sleep(100);
                GameProgress.findImageAndClick("images\\playagain.png");
                Thread.sleep(100);
                GameProgress.findImageAndClick("images\\FindMatch.png");
            }
        }
    }
}
