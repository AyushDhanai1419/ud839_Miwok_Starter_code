package com.example.android.miwok;

/*
  Created by hp on 02-07-2017.
 */

public class Word {
    private String mDefaultTranslation;

    private String mMewokTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String DefaultTranslation, String mewokTranslation, int imageResourceId) {
        mDefaultTranslation = DefaultTranslation;
        mMewokTranslation = mewokTranslation;
        mImageResourceId = imageResourceId;
    }

    public Word(String DefaultTranslation, String mewokTranslation) {
        mDefaultTranslation = DefaultTranslation;
        mMewokTranslation = mewokTranslation;

    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMewokTranslation() {
        return mMewokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
