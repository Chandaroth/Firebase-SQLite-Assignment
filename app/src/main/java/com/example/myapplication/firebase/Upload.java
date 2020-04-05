package com.example.myapplication.firebase;
import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private String mPrice;
    private int position;

    public Upload (int position){
        this.position = position;
    }

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String price) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mPrice=price;
    }

    public String getName() {
        return mName;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }


}