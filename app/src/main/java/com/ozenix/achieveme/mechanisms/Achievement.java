package com.ozenix.achieveme.mechanisms;


public class Achievement {
    private String name;
    private Double latitude;
    private Double longitude;
    private String text;

    private String imageUrl;
    private Category category;

    public Achievement(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
