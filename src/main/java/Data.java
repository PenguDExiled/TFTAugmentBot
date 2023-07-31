import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Data {

    /**
     * Makes a post request to the database.
     *
     * @param augment The augment data to be added.
     * @param rarity The rarity data to be added.
     * @param stage The stage data to be added.
     * @param user The username data to be added.
     * @param gameid The gameid data to be added.
     */
    public static void addData(String augment, String rarity, String stage, String user, String gameid, String tftSet, boolean xd) {
        try {
            String boundary = "----CustomBoundary123"; // Eindeutige Boundary-Zeichenfolge hier festlegen
            File imageFile = new File("endboard.png");
            String url = "https://apex.oracle.com/pls/apex/leonworkspace/new/augment";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Construct the request body
            try (OutputStream os = con.getOutputStream()) {
                String formDataTemplate = "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"%s\"\r\n\r\n%s\r\n";

                // Add other form fields
                os.write(String.format(formDataTemplate, "augment", augment).getBytes(StandardCharsets.UTF_8));
                os.write(String.format(formDataTemplate, "rarity", rarity).getBytes(StandardCharsets.UTF_8));
                os.write(String.format(formDataTemplate, "stage", stage).getBytes(StandardCharsets.UTF_8));
                os.write(String.format(formDataTemplate, "username", user).getBytes(StandardCharsets.UTF_8));
                os.write(String.format(formDataTemplate, "gameid", gameid).getBytes(StandardCharsets.UTF_8));
                os.write(String.format(formDataTemplate, "tft_set", tftSet).getBytes(StandardCharsets.UTF_8));

                // Add the image file
                os.write(("--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"filename\"; filename=\"" + imageFile.getName() + "\"\r\n" +
                        "Content-Type: image/png\r\n\r\n").getBytes(StandardCharsets.UTF_8));

                try (FileInputStream fis = new FileInputStream(imageFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }

                os.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Wenn die Antwort erfolgreich ist, kannst du den Antworttext auslesen
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println("Response Body: " + response.toString());
                }
            } else {
                // Wenn die Antwort nicht erfolgreich ist, kannst du den Fehler auslesen
                try (BufferedReader errorIn = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String errorLine;
                    StringBuffer errorResponse = new StringBuffer();
                    while ((errorLine = errorIn.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    System.err.println("Error Response: " + errorResponse.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addData(String augment, String rarity, String stage, String user, String gameid, String tftSet) {
        try {
            String url = "https://apex.oracle.com/pls/apex/leonworkspace/new/augment";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            // Hier setzen wir den Content-Type auf "multipart/form-data"
            String boundary = "*****";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Erstelle den Request Body
            StringBuilder requestBodyBuilder = new StringBuilder();
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"augment\"\r\n\r\n");
            requestBodyBuilder.append(augment).append("\r\n");
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"rarity\"\r\n\r\n");
            requestBodyBuilder.append(rarity).append("\r\n");
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"stage\"\r\n\r\n");
            requestBodyBuilder.append(stage).append("\r\n");
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"username\"\r\n\r\n");
            requestBodyBuilder.append(user).append("\r\n");
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"gameid\"\r\n\r\n");
            requestBodyBuilder.append(gameid).append("\r\n");
            requestBodyBuilder.append("--").append(boundary).append("\r\n");
            requestBodyBuilder.append("Content-Disposition: form-data; name=\"tft_set\"\r\n\r\n");
            requestBodyBuilder.append(tftSet).append("\r\n");

            // Wenn du ein Bild hast, füge es zum Request Body hinzu
            // Ersetze "path/to/your/image.jpg" durch den tatsächlichen Pfad zu deinem Bild
            File imageFile = new File("path/to/your/image.jpg");
            if (imageFile.exists()) {
                requestBodyBuilder.append("--").append(boundary).append("\r\n");
                requestBodyBuilder.append("Content-Disposition: form-data; name=\"body\"; filename=\"image.jpg\"\r\n");
                requestBodyBuilder.append("Content-Type: image/jpeg\r\n\r\n");
            }

            requestBodyBuilder.append("--").append(boundary).append("--\r\n");

            byte[] postData = requestBodyBuilder.toString().getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream dos = new DataOutputStream(con.getOutputStream())) {
                dos.write(postData);

                // Wenn du ein Bild hast, sende es
                if (imageFile.exists()) {
                    byte[] imageData = java.nio.file.Files.readAllBytes(imageFile.toPath());
                    dos.write(imageData);
                    dos.write("\r\n".getBytes(StandardCharsets.UTF_8));
                }
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
