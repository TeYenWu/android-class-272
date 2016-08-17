package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/16.
 */
public class SimpleUIApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Drink.class);
        ParseObject.registerSubclass(DrinkOrder.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("5TzONJKwcXZzvLRTNOEC88iAvgP7rgp2SpL9Cwkq")
                .server("https://parseapi.back4app.com/")
                .clientKey("mB4vIj1bCPErpyOJUfRn51n2XBqnJDxUqH0BT6PY")
                .build()
        );
    }
}
