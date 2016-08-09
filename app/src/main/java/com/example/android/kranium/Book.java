package com.example.android.kranium;

/**
 * Created by keane on 7/28/2016.
 */
public class Book {
    private String mTitle;
    private String mAuthor;

    public Book(String t, String a){
        mTitle = t;
        mAuthor = a;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }
}
