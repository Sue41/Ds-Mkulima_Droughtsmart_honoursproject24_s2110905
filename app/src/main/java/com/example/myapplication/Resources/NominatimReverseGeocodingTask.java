package com.example.myapplication.Resources;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NominatimReverseGeocodingTask extends AsyncTask<Double, Void, String> {
    private static final String TAG = "NominatimReverseGeocodingTask";
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f&zoom=18&addressdetails=1";

    private GeocodingListener listener;

    public NominatimReverseGeocodingTask(GeocodingListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Double... params) {
        double latitude = params[0];
        double longitude = params[1];
        String url = String.format(NOMINATIM_URL, latitude, longitude);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONObject address = jsonObject.getJSONObject("address");
                String city = address.optString("city", address.optString("town", address.optString("village", "Unknown city")));
                String country = address.optString("country", "Unknown country");
                return city + ", " + country;
            } else {
                Log.e(TAG, "Geocoding request failed: " + response.message());
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoding request failed", e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onGeocodingResult(result);
        }
    }

    public interface GeocodingListener {
        void onGeocodingResult(String address);
    }
}
