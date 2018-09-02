package com.mickeywilliamson.project8;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import net.danlew.android.joda.JodaTimeAndroid;

public class GTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        JodaTimeAndroid.init(this);
    }
}
