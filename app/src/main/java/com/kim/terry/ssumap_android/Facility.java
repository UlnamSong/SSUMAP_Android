package com.kim.terry.ssumap_android;

/**
 * Created by terry on 2017. 11. 26..
 */

public class Facility {
    private String name;
    private int image;

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Facility(String name, int image) {
        this.name = name;
        this.image = image;
    }
}
