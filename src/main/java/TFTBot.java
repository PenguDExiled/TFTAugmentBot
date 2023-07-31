import nu.pattern.OpenCV;
import javax.swing.*;
import java.util.List;

public class TFTBot {
    static final String tftSet = "9.0";
    public static void main(String[] args) throws Exception {
        OpenCV.loadLocally();
        SwingUtilities.invokeLater(GUI::new);
    }
    public static void Run(String username, GUI gui) throws Exception {
        new Thread(() -> {
            int counter = 0;
            double[] tmp;
            boolean isFindMatch = false;
            boolean isAccepted = false;
            while (true){
                try {
                    if(!gui.checkIsRunning()){
                        break;
                    }
                    if(!isFindMatch&&(tmp = ImageScanner.isOnScreen("images\\FindMatch.png"))[0] != -99999) {
                        System.out.println("found");
                        GameProgress.moveMouseAndClick((int)tmp[0], (int) tmp[1]);
                        isFindMatch = true;
                    }
                    else {
                        gui.setInfoArea(updateInfoText("cant find FindMatch-Button"));
                        System.out.printf("cant find FindMatch");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(!isAccepted&&isFindMatch && (tmp = ImageScanner.isOnScreen("images\\Accept.png"))[0] != -99999){
                        System.out.println("found");
                        GameProgress.moveMouseAndClick((int)tmp[0], (int) tmp[1]);
                        isAccepted = true;
                    }
                    else {
                        gui.setInfoArea(updateInfoText("cant find Accept-Button"));
                        System.out.println("cant find Accept Button");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(isAccepted){
                    isAccepted = false;
                    isFindMatch = false;
                    counter++;
                    gui.setInfoArea(updateInfoText("New Game! Game Number: " + counter));
                    System.out.println("New Game! Game Number: " + counter);
                    try {
                        RunGame(username, gui);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();

    }
    public static void RunGame(String username, GUI gui) throws Exception {
        System.out.println("game in progress...");
        String gameID = RandomStringGenerator.generateRandomString();
        List<String> augments;
        String stage = "1-1";
        String scannedAugments = "";
        String task = "";
        double[] tempPos;
        int stagecount = 1;
        String rarity;
        double[] tmp;
        boolean is1_3 = false;
        boolean is1_4 = false;
        boolean is2_1= false;
        boolean is2_2= false;
        boolean is2_3= false;
        boolean is2_5 = false;
        boolean is2_6= false;
        boolean is2_7= false;
        boolean is3_1= false;
        boolean is3_2 = false;
        boolean is3_3 = false;
        boolean is3_5 = false;
        boolean is3_6 = false;
        boolean is3_7 = false;
        boolean is4_1 = false;
        boolean is4_2 = false;
        boolean is4_3 = false;
        boolean isControl = false;
        boolean isForfeitCheck = false;
        boolean isFF = false;
        boolean isPlayAgain = false;
        boolean isForfeit = false;
        while (stagecount<4){
            if(!gui.checkIsRunning()){
                break;
            }


            if(!is1_3 && (tempPos = ImageScanner.isOnScreen("images\\1-3.png"))[0] != -99999){
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("1-3");
                GameProgress.sellUnits();
                stage = "1-3";
                is1_3 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is1_4 && is1_3 && (tempPos = ImageScanner.isOnScreen("images\\1-4.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("1-4");
                GameProgress.sellUnits();
                stage = "1-4";
                is1_4 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is2_1 && is1_4 && (tempPos = ImageScanner.isOnScreen("images\\2-1.png"))[0] != -99999) {
                task = "reading augments...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-1");
                stage = "2-1";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2) + " " + rarity + " " + stage + " " + username + " " + gameID + " " + tftSet);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);
                scannedAugments += "Augments from stage: " + stage + "is " + rarity + "\naugment 1: " + augments.get(0) + "\naugment 2: " + augments.get(1) + "\naugment 3: " + augments.get(2) +"\n";
                GameProgress.rerollAugments();
                augments.clear();
                augments = TextRecognition.imageToString();
                System.out.println(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2) + " " + rarity + " " + stage + " " + username + " " + gameID + " " + tftSet);
                GameProgress.takeAugment();
                GameProgress.sellUnits();
                ScreenCapture.takeScreenshot("endboard", true);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);

                is2_1 = true;
                scannedAugments += "augment 4: " + augments.get(0) + "\naugment 5: " + augments.get(1) + "\naugment 6: " + augments.get(2) + "\ntook: " + augments.get(0) + "\n";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username, scannedAugments));
            } else if (!is2_2 && is2_1 && (tempPos = ImageScanner.isOnScreen("images\\2-2.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-2");
                GameProgress.sellUnits();
                stage = "2-2";
                is2_2 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            }else if (!is2_3 && is2_2 && (tempPos = ImageScanner.isOnScreen("images\\2-3.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-3");
                GameProgress.sellUnits();
                stage = "2-3";
                is2_3 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            }else if (!is2_5 && is2_3 && (tempPos = ImageScanner.isOnScreen("images\\2-5.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-5");
                GameProgress.sellUnits();
                stage = "2-5";
                is2_5 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is2_6 && is2_5 && (tempPos = ImageScanner.isOnScreen("images\\2-6.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-6");
                GameProgress.sellUnits();
                stage = "2-6";
                is2_6 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            }else if (!is2_7 && is2_6 && (tempPos = ImageScanner.isOnScreen("images\\2-7.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("2-7");
                GameProgress.sellUnits();
                stage = "2-7";
                is2_7 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is3_1 && is2_7 && (tempPos = ImageScanner.isOnScreen("images\\3-1.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-1");
                GameProgress.sellUnits();
                stage = "3-1";
                is3_1 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is3_2 && is3_1 && (tempPos = ImageScanner.isOnScreen("images\\3-2.png"))[0] != -99999) {
                task = "reading augments...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-2");
                stage = "3-2";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.toString() + " " + rarity + " " + stage + " " + username + " " + gameID);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);
                scannedAugments += "Augments from stage: " + stage + "is " + rarity + "\naugment 1: " + augments.get(0) + "\naugment 2: " + augments.get(1) + "\naugment 3: " + augments.get(2) +"\n";
                GameProgress.rerollAugments();
                augments.clear();
                augments = TextRecognition.imageToString();
                System.out.println(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2) + " " + rarity + " " + stage + " " + username + " " + gameID + " " + tftSet);
                GameProgress.takeAugment();
                GameProgress.sellUnits();
                ScreenCapture.takeScreenshot("endboard", true);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);
                is3_2 = true;
                scannedAugments += "augment 4: " + augments.get(0) + "\naugment 5: " + augments.get(1) + "\naugment 6: " + augments.get(2) + "\ntook: " + augments.get(0) + "\n";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username, scannedAugments));
            } else if (!is3_3 && is3_2 && (tempPos = ImageScanner.isOnScreen("images\\3-3.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-3");
                GameProgress.sellUnits();
                stage = "3-3";
                is3_3 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is3_5 && is3_3 && (tempPos = ImageScanner.isOnScreen("images\\3-5.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-5");
                GameProgress.sellUnits();
                stage = "3-5";
                is3_5 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is3_6 && is3_5 && (tempPos = ImageScanner.isOnScreen("images\\3-6.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-6");
                GameProgress.sellUnits();
                stage = "3-6";
                is3_6 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is3_7 && is3_6 && (tempPos = ImageScanner.isOnScreen("images\\3-7.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("3-7");
                GameProgress.sellUnits();
                stage = "3-7";
                is3_7 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is4_1 && is3_7 && (tempPos = ImageScanner.isOnScreen("images\\4-1.png"))[0] != -99999) {
                task = "selling units...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("4-1");
                GameProgress.sellUnits();
                stage = "4-1";
                is4_1 = true;
                task = "bing chilling";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
            } else if (!is4_2 && is4_1 && (tempPos = ImageScanner.isOnScreen("images\\4-2.png"))[0] != -99999) {
                task = "reading augments...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username));
                System.out.println("4-2");
                stage = "4-2";
                stagecount++;
                augments = TextRecognition.imageToString();
                rarity = ScreenAnalyzer.getPixelColour();
                System.out.println(augments.toString() + " " + rarity + " " + stage + " " + username + " " + gameID);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);
                scannedAugments += "Augments from stage: " + stage + "is " + rarity + "\naugment 1: " + augments.get(0) + "\naugment 2: " + augments.get(1) + "\naugment 3: " + augments.get(2) +"\n";
                GameProgress.rerollAugments();
                augments.clear();
                augments = TextRecognition.imageToString();
                System.out.println(augments.get(0) + ", " + augments.get(1) + "," + augments.get(2) + " " + rarity + " " + stage + " " + username + " " + gameID + " " + tftSet);
                GameProgress.takeAugment();
                GameProgress.sellUnits();
                ScreenCapture.takeScreenshot("endboard", true);
                Data.addData(augments.get(0) + ", " + augments.get(1) + ", " + augments.get(2), rarity,stage,username,gameID, tftSet);
                is4_2 = true;
                scannedAugments += "augment 4: " + augments.get(0) + "\naugment 5: " + augments.get(1) + "\naugment 6: " + augments.get(2) + "\ntook: " + augments.get(0) + "\n";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username, scannedAugments));
            }
            else {
                task = "Cannot find next Stage. Current Stage: " + stage;
                gui.setInfoArea(updateInfoText(stage, task, gameID, username, scannedAugments));
            }
        }
        while (!isForfeit){
            if(!gui.checkIsRunning()){
                break;
            }
            if(!is4_3 && (tempPos = ImageScanner.isOnScreen("images\\4-3.png"))[0] != -99999){
                task = "surrendering...";
                gui.setInfoArea(updateInfoText(stage, task, gameID, username, scannedAugments));
                is4_3 = true;
            }
            else if(!isControl && is4_3 && (tempPos = ImageScanner.isOnScreen("images\\control.png"))[0] != -99999){
                GameProgress.findImageAndClick("images\\control.png");
                isControl = true;
            }
            else if(!isForfeitCheck && isControl && (tempPos = ImageScanner.isOnScreen("images\\forfeit.png"))[0] != -99999){
                GameProgress.findImageAndClick("images\\forfeit.png");
                isForfeitCheck = true;
            }
            else if(!isFF && isForfeitCheck && (tempPos = ImageScanner.isOnScreen("images\\ffbutton.png"))[0] != -99999){
                GameProgress.findImageAndClick("images\\ffbutton.png");
                isFF = true;
            }
            else if(!isPlayAgain && isFF && (tempPos = ImageScanner.isOnScreen("images\\playagain.png"))[0] != -99999){
                GameProgress.findImageAndClick("images\\playagain.png");
                isPlayAgain = true;
                isForfeit = true;
            }
        }
    }

    public static String updateInfoText(String stage, String task,String gameID, String playername, String augments){
        String infoText = "Username: " + playername + "\n" + "GameID: " +  gameID + "\n" + "Stage: " + stage + "\n\n" + task + "\n" + augments;
        return infoText;
    }
    public static String updateInfoText(String stage, String task,String gameID, String playername){
        String infoText = "Username: " + playername + "\n" + "GameID: " +  gameID + "\n" + "Stage: " + stage  + "\n\n" + task;
        return infoText;
    }
    public static String updateInfoText(String task){
        String infoText = task;
        return infoText;
    }
}
