package me.ihaq.imgurhelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImgurHelper {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String apiKey;

    public ImgurHelper(String apiKey) {
        this.apiKey = apiKey;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}