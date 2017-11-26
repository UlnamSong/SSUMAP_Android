package com.kim.terry.ssumap_android;

/**
 * Created by terry on 2017. 11. 26..
 */

public class Spot {
    private String name;
    private String phoneNumber;
    private double longitude;
    private double latitude;
    private String fileName;
    private String address;
    private String description;

    public Spot(String name, String fileName, String address, String description, double longitude, double latitude, String phoneNumber) {
        this.name = name;
        this.fileName = fileName;
        this.address = address;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
