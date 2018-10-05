package com.mickeywilliamson.project8.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickeywilliamson.project8.Models.Hour;


public class DatabaseUpdateIntentService extends IntentService {



    public DatabaseUpdateIntentService() {
        super("DatabaseUpdateIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Hour hour = intent.getParcelableExtra("HOUR");
        String path = intent.getStringExtra("PATH");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(String.valueOf(hour.getMilitaryHour())).setValue(hour);
    }
}
