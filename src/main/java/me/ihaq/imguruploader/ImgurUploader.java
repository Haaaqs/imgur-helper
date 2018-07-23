package me.ihaq.imguruploader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import me.ihaq.imguruploader.exception.ImgurUploaderException;
import me.ihaq.imguruploader.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImgurUploader {

    private final String ENDPOINT = "https://api.imgur.com/3/image";

    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String apiKey;

    public ImgurUploader(String apiKey) {
        this.apiKey = apiKey;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public JsonObject uploadSync(File image) throws IOException, ImgurUploaderException {
        return uploadImage(encodeImage(image));
    }

    public void uploadAsync(Callback callback, File image) {
        executorService.submit(() -> {
            try {
                callback.onSuccess(uploadImage(encodeImage(image)));
            } catch (IOException | ImgurUploaderException e) {
                callback.onFail(e);
            }
        });
    }

    private String encodeImage(File file) throws IOException, ImgurUploaderException {
        BufferedImage image = ImageIO.read(file);

        if (image == null) {
            throw new ImgurUploaderException("File is not an image.");
        }

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArray);
        byte[] byteImage = byteArray.toByteArray();
        String dataImage = Base64.encode(byteImage);
        return "image=" + URLEncoder.encode(dataImage, "UTF-8");
    }

    private JsonObject uploadImage(String imageData) throws ImgurUploaderException, IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Client-ID " + apiKey);
        connection.connect();

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(imageData);
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        reader.close();

        JsonObject dataObject = jsonObject.get("data").getAsJsonObject();

        if (jsonObject.has("success") && jsonObject.get("success").getAsBoolean()) {
            return dataObject;
        }

        throw new ImgurUploaderException(dataObject.get("error").getAsString());
    }

}