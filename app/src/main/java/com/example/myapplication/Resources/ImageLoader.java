package com.example.myapplication.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ImageLoader {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void loadImage(String url, ImageView imageView) {
        executorService.submit(() -> {
            Bitmap bitmap = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("User-agent", "Mozilla/4.0");
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bitmap != null) {
                // Ensure to update the UI on the main thread
                Bitmap finalBitmap = bitmap;
                imageView.post(() -> imageView.setImageBitmap(finalBitmap));
            }
        });
    }
}

