package com.example.myapplication;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.myapplication.ml.ModelUnquant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ScannerContent extends Fragment {

    private TextView clickText, disease_text, clickablelink;
    private ImageView show_click, diagoniseimage;
    private FloatingActionButton button;
    int imageSize = 224;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_content, container, false);

        clickText = view.findViewById(R.id.show_click_text);
        disease_text = view.findViewById(R.id.disease_text);
        clickablelink = view.findViewById(R.id.clickablelink);
        show_click = view.findViewById(R.id.show_click);
        diagoniseimage = view.findViewById(R.id.diagoniseimage);
        button = view.findViewById(R.id.fab);

        clickText.setVisibility(View.VISIBLE);
        clickText.setText("Click the button below to upload Image");
        show_click.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            if (image != null) {
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                diagoniseimage.setImageBitmap(image);

                clickText.setVisibility(View.GONE);
                clickText.setText("Click the button below to upload Image");
                show_click.setVisibility(View.GONE);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void classifyImage(Bitmap image) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(getContext());
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }

            String[] classes = {
                    "Apple Scrab",
                    "Apple Black rot",
                    "Apple Cedar apple rust",
                    "Cherry Powdery mildew",
                    "Maize corn Cercospora leaf spot and Gray leaf spot",
                    "Maize Corn Common rust_",
                    "Maize Corn Northern Leaf Blight",
                    "Grape Black rot",
                    "Grape Esca(Black_Measles)",
                    "Grape Leaf blight(Isariopsis_Leaf_Spot)",
                    "Orange Haunglongbing(Citrus_greening)",
                    "Peach Bacterial spot",
                    "Pepper bell Bacterial_spot",
                    "Potato Early blight",
                    "Potato Late blight",
                    "Squash Powdery mildew",
                    "Strawberry Leaf scorch",
                    "Tomato Bacterial spot",
                    "Tomato Early blight",
                    "Tomato Late blight",
                    "Tomato Leaf Mold",
                    "Tomato Septoria leaf spot",
                    "Tomato Spider mites Two spotted spider mite",
                    "Tomato Target Spot",
                    "Tomato mosaic virus",
                    "Tomato Yellow Leaf Curl Virus"
            };

            disease_text.setText(classes[maxPos]);
            disease_text.setTextColor(ContextCompat.getColor(getContext(), R.color.red_A700));
            disease_text.setVisibility(View.VISIBLE);
            clickablelink.setTextColor(Color.parseColor("#0000EE"));;
            clickablelink.setText(Html.fromHtml("<u>Click here to see result online</u>"));
            //clickablelink.setPaintFlags(disease_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            clickablelink.setVisibility(View.VISIBLE);

            clickablelink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + disease_text.getText().toString())));
                }
            });
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}