package com.example.myapplication;

import android.content.Context;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.tensorflow.lite.Interpreter;

public class CropRecommendation {
    private Interpreter tflite;
    private String[] classNames;

    public CropRecommendation(Context context) throws IOException {
        MappedByteBuffer model = loadModelFile(context);
        tflite = new Interpreter(model);
        classNames = new String[] {
                "Apple", "Banana", "Blackgram", "ChickPea", "Coconut", "Coffee", "Cotton",
                "Grapes", "Jute", "KidneyBeans", "Lentil", "Maize", "Mango", "MothBeans",
                "MungBean", "Muskmelon", "Orange", "Papaya", "PigeonPeas", "Pomegranate",
                "Rice", "Watermelon"
        };
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor fileDescriptor = assetManager.openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long length = fileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }

    public String recommendCrop(float[] inputData) {
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4 * inputData.length);
        inputBuffer.order(ByteOrder.nativeOrder());
        for (float value : inputData) {
            inputBuffer.putFloat(value);
        }
        inputBuffer.rewind();

        float[][] output = new float[1][classNames.length];
        tflite.run(inputBuffer, output);

        int maxIndex = 0;
        for (int i = 1; i < output[0].length; i++) {
            if (output[0][i] > output[0][maxIndex]) {
                maxIndex = i;
            }
        }

        return classNames[maxIndex];
    }
}
