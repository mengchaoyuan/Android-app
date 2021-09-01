package com.example.photo_1;

import java.util.ResourceBundle;

public class Beauty {
    /**
     * 名字
     */
    private String name;
    /**
     * 图片id
     */
    private String imagesrc;

    public Beauty(String name, String imageId) {
        this.name = name;
        this.imagesrc = imageId;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imagesrc;
    }


}
