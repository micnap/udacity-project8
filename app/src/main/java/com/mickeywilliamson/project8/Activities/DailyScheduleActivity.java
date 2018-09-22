package com.mickeywilliamson.project8.Activities;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildware.widget.indeterm.IndeterminateCheckBox;
import com.facebook.login.LoginManager;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mickeywilliamson.project8.Fragments.DailyScheduleFragment;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import com.mickeywilliamson.project8.Fragments.DatePickerFragment;
import com.mickeywilliamson.project8.Fragments.HourlyTaskDialogFragment;
import com.mickeywilliamson.project8.Models.Hour;
import com.mickeywilliamson.project8.Models.Supplement;
import com.mickeywilliamson.project8.Models.ProtocolNonMalignant;

public class DailyScheduleActivity extends AppCompatActivity implements
        HourlyTaskDialogFragment.HourlyTaskDialogListener,
        DatePickerFragment.OnFragmentInteractionListener {

    private String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        if (getIntent().getStringExtra("query_date") != null) {
            chosenDate = getIntent().getStringExtra("query_date");
        } else {
            chosenDate = new LocalDate().toString();
        }

        setTitle(chosenDate);
        DailyScheduleFragment dailyScheduleFragment = new DailyScheduleFragment();
        dailyScheduleFragment.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.daily_schedule_fragment, dailyScheduleFragment.newInstance(1, chosenDate), "DAILYSCHEDULEFRAGMENT");
        fragmentTransaction.commit();
    }

    // Returns data from the date picker dialog.
    @Override
    public void onFragmentInteraction(String date) {

        Log.d("DATE", date);
        chosenDate = date;

        setTitle(date);

        Bundle bundle = new Bundle();
        bundle.putInt("column-count", 1);
        bundle.putString("CHOSENDATE", date);
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        DailyScheduleFragment newFragment = new DailyScheduleFragment();
        newFragment.setArguments(bundle);
        fragTransaction.replace(R.id.daily_schedule_fragment, newFragment.newInstance(1, date));
        fragTransaction.commit();
    }

    // Returns data from the Hourly Dialog list of tasks
    @Override
    public void onDialogPositiveClick(Bundle bundle) {

        DailyScheduleFragment dailyScheduleFragment = (DailyScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.daily_schedule_fragment);
        dailyScheduleFragment.reload(bundle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_pick_date:
                String[] dateParts = chosenDate.split("-");
                Bundle date = new Bundle();
                date.putInt("Year", Integer.valueOf(dateParts[0]));
                date.putInt("Month", Integer.valueOf(dateParts[1]));
                date.putInt("Day", Integer.valueOf(dateParts[2]));
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(date);
                newFragment.show(getSupportFragmentManager(), "date picker");

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
