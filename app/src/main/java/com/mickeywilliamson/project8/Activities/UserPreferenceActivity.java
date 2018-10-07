package com.mickeywilliamson.project8.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mickeywilliamson.project8.R;

import sharefirebasepreferences.crysxd.de.lib.SharedFirebasePreferences;
import sharefirebasepreferences.crysxd.de.lib.SharedFirebasePreferencesContextWrapper;


public class UserPreferenceActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "prefactivity";
    private SharedFirebasePreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseAuth.getInstance().addAuthStateListener(this);

        getPrefs(FirebaseAuth.getInstance());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreferences != null) {
            mPreferences.keepSynced(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPreferences != null) {
            mPreferences.keepSynced(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreferences != null) {
            mPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new SharedFirebasePreferencesContextWrapper(newBase));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        getPrefs(firebaseAuth);
    }

    private void getPrefs(FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null) {
            mPreferences = SharedFirebasePreferences.getDefaultInstance(this);
            mPreferences.keepSynced(true);
            mPreferences.registerOnSharedPreferenceChangeListener(this);
            mPreferences.pull().addOnPullCompleteListener(new SharedFirebasePreferences.OnPullCompleteListener() {
                @Override
                public void onPullSucceeded(SharedFirebasePreferences preferences) {
                    //showView();
                }

                @Override
                public void onPullFailed(Exception e) {
                    //showView();
                    Toast.makeText(UserPreferenceActivity.this, "Fetch failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
