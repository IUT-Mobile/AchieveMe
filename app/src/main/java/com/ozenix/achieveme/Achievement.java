package com.ozenix.achieveme;


public class Achievement {
    private String text;
    private String imageUrl;
    private Category category;

    public Achievement(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
