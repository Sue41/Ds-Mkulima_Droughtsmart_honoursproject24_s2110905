package com.example.myapplication.Resources;

public class HomeItemContent {
    private int imageResId;
    private String text;
    private String buttonText;

    public HomeItemContent(int imageResId, String text, String buttonText) {
        this.imageResId = imageResId;
        this.text = text;
        this.buttonText = buttonText;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getButtonText() {
        return buttonText;
    }
}
