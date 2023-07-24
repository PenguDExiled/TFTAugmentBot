import java.util.Random;

public class RandomStringGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;

    public static String generateRandomString() {
        StringBuilder randomString = new StringBuilder(STRING_LENGTH);
        Random random = new Random();

        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}