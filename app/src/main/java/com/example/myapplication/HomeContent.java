package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.myapplication.Resources.NominatimReverseGeocodingTask;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.location.Location;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeContent extends Fragment {
    private FusedLocationProviderClient fusedLocationClient;
    private  TextView location;
    private  TextView temparature;
    private  TextView condition;
    private  TextView temphl;
    private ImageView conditionLayout;
    private OnFragmentInteractionListener mListener;
    private ExecutorService executor = Executors.newSingleThreadExecutor();



    public interface OnFragmentInteractionListener {
        void onButtonClicked();
        void onButtonClicked2();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_content, container, false);
        final LinearLayout predict = view.findViewById(R.id.diagonise_button_icon_container);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        location = view.findViewById(R.id.location);
        temphl = view.findViewById(R.id.h_32_l_27_e);
        condition = view.findViewById(R.id.condition_text);
        temparature = view.findViewById(R.id.tempature);
        conditionLayout = view.findViewById(R.id.condition);

        final LinearLayout scan = view.findViewById(R.id.scan_button_icon_container);
        final LinearLayout irrigation = view.findViewById(R.id.irrigation_button_icon_container);
        final LinearLayout soilstatus = view.findViewById(R.id.soil_button_icon_container);
        final LinearLayout consult = view.findViewById(R.id.consult_button_icon_container);

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Thank you for your interest in DroughtSmart. We're here to assist you with any questions or support you need regarding our products and services. Feel free to reach out to us, and we'll get back to you as soon as possible.";
                Intent intent = new Intent(getContext(),ReusableCoponent.class);
                intent.putExtra("title","Contact us");
                intent.putExtra("icon","help_item_icon");
                intent.putExtra("text",msg);
                startActivity(intent);
            }
        });


        soilstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Soil.class));
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PredictPlant.class));
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onButtonClicked();
                }
            }
        });
        irrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onButtonClicked2();
                }
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getCurrentLocation();
        }

        return view;
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location1 = task.getResult();
                            double latitude = location1.getLatitude();
                            double longitude = location1.getLongitude();
                            fetchWeatherData(latitude, longitude);
                            //getAddressFromLocation(latitude, longitude);
                        } else {
                            location.setText("Unable to get location");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }
    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = address.getAddressLine(0); // Full address
                location.setText(addressText);
                fetchWeatherData(latitude, longitude);
            } else {
                // If Geocoder fails, use Nominatim
                new NominatimReverseGeocodingTask(new NominatimReverseGeocodingTask.GeocodingListener() {
                    @Override
                    public void onGeocodingResult(String address) {
                        if (address != null) {
                            location.setText(address);
                        } else {
                            location.setText("Address not found");
                        }
                        fetchWeatherData(latitude, longitude);
                    }
                }).execute(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // If Geocoder fails, use Nominatim
            new NominatimReverseGeocodingTask(new NominatimReverseGeocodingTask.GeocodingListener() {
                @Override
                public void onGeocodingResult(String address) {
                    if (address != null) {
                        location.setText(address);
                    } else {
                        location.setText("Unable to get address");
                    }
                    fetchWeatherData(latitude, longitude);
                }
            }).execute(latitude, longitude);
        }
    }
    private void fetchWeatherData(double latitude, double longitude) {
        String apiKey = "df3378139d476c5960ddc77059318703";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

        new GetWeatherTask().execute(apiUrl);
    }

    private class GetWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject main = jsonObject.getJSONObject("main");
                    double temp = main.getDouble("temp") - 273.15;
                    double tempMin = main.getDouble("temp_min") - 273.15;
                    double tempMax = main.getDouble("temp_max") - 273.15;
                    String weatherInfo = String.format("%.0f", temp) + "°";
                    String minTempInfo = String.format("%.0f", tempMin) + "°";
                    String maxTempInfo = String.format("%.0f", tempMax) + "°";


                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String condition_string = weather.getString("description");
                    String icon = weather.getString("icon");
                    String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
                    System.out.println(iconUrl);

                    temparature.setText(weatherInfo);
                    condition.setText(condition_string);
                    temphl.setText("H:" + maxTempInfo + " L:" + minTempInfo);

                    loadImage(conditionLayout,iconUrl);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
            }
        }


    }

    public void loadImage(final ImageView imageView, final String url) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = bitmapFromUrl(url);
                    if (bitmap != null) {
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Bitmap bitmapFromUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        try (InputStream input = connection.getInputStream()) {
            return BitmapFactory.decodeStream(input);
        }
    }

}