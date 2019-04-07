package com.example.bookmanagement;

import android.app.Application;

public class BaseApplication extends Application {

    public static BookDBHelper bookDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        bookDBHelper = new BookDBHelper(this, "book.sqlite", null, 1);
    }
}
