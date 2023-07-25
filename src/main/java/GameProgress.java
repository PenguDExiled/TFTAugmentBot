import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
public class GameProgress{
    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rerollAugments() throws InterruptedException, AWTException {
        System.out.println("rerolling augments"); //x 550,y=860 x=950,y=860 x=1350,y=860
        Thread.sleep(1000);
        moveMouseAndClick(550,860); // first augment
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
        moveMouseAndClick(950,860); // second augment
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
        moveMouseAndClick(1350,860); // third augment
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
    }

    public static void sellUnits() throws AWTException, InterruptedException {
        System.out.println("sells units");
        Thread.sleep(2000);
        moveMouseAndClick(964, 648);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
        moveMouseAndClick(440, 776);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
        moveMouseAndClick(538,759);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
        moveMouseAndClick(659,776);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(100);
        robot.keyRelease(KeyEvent.VK_E);
        Thread.sleep(1000);
    }
    public static void moveMouseAndClick(int x, int y) throws AWTException, InterruptedException {
        System.out.println("clicks at: " +x + ", " + y);
        robot.mouseMove(x,y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void takeAugment() throws AWTException, InterruptedException {
        System.out.println("takes augment");
        moveMouseAndClick(546, 621);
    }
    public static void findImageAndClick(String imagePath) throws Exception{
        System.out.println("searches image: " + imagePath);
        boolean clicked = false;
        double[] position = new double[2];
        position = ImageScanner.isOnScreen(imagePath);
        while(!clicked){
            if(position[0] != -99999){
                System.out.println("found image: " + imagePath);
                moveMouseAndClick((int)position[0], (int)position[1]);
                clicked = true;
            }
            else{
                System.out.println("didnt find: " + imagePath);
            }
        }
    }
}
