package edu.westfieldstate.eticketmanager.util;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GifUtil {

    private static final String API_KEY = "YNEaBUYS6ptZZvwnoTx8kzblohsLTHEH";

    public static void loadRandomGifIntoImageView(ImageView imageView, String tag) {
        new Thread(() -> {
            try {
                String apiUrl = "https://api.giphy.com/v1/gifs/random?api_key=" + API_KEY + "&tag=" + tag;
                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(responseBuilder.toString());
                String gifUrl = json.getJSONObject("data")
                        .getJSONObject("images")
                        .getJSONObject("original")
                        .getString("url");

                Platform.runLater(() -> {
                    Image gifImage = new Image(gifUrl, true); // background loading
                    imageView.setImage(gifImage);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
