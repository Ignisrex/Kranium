package com.example.android.kranium;

/**
 * Created by keane on 7/28/2016.
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDescription;

    public Book(String t, String a,String d){
        mTitle = t;
        mAuthor = a;
        mDescription=d;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription(){
        return mDescription;
    }
}
