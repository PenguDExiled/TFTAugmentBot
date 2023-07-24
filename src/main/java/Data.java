import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Data{

    /**
     * Makes a post request to the database.
     *
     * @param augment The augment data to be added.
     * @param rarity The rarity data to be added.
     * @param stage The stage data to be added.
     * @param user The username data to be added.
     * @param gameid The gameid data to be added.
     */
    public static void addData(String augment, String rarity, String stage, String user, String gameid) {
        try {
            String url = "https://apex.oracle.com/pls/apex/leonworkspace/new/augment";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String parameters = "augment=" + augment + "&rarity=" + rarity + "&stage=" + stage +
                    "&username=" + user + "&gameid=" + gameid;
            byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = con.getOutputStream()) {
                os.write(postData);
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
