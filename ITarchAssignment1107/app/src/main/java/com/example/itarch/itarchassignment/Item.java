package com.example.itarch.itarchassignment;

/**
 * Created by b1013043 on 2016/11/03.
 */

public class Item {

    // 記事のタイトル
    // 記事の本文
    private CharSequence mTitle;
    private CharSequence mDescription;

    public Item() {
        mTitle = "";
        mDescription = "";
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }
}
