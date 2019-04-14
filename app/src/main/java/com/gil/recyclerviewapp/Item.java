package com.gil.recyclerviewapp;

public class Item {

    private String imageUrl;
    private String mCreator;
    private int mLikes;

    public Item(String imageUrl, String mCreator, int mLikes) {
        this.imageUrl = imageUrl;
        this.mCreator = mCreator;
        this.mLikes = mLikes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getmCreator() {
        return mCreator;
    }

    public int getmLikes() {
        return mLikes;
    }
}
