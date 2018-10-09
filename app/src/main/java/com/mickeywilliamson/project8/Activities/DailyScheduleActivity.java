package com.mickeywilliamson.project8.Activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.TimeZone;

import com.mickeywilliamson.project8.Fragments.DatePickerFragment;
import com.mickeywilliamson.project8.Fragments.HourlyTaskDialogFragment;
import com.mickeywilliamson.project8.Models.Hour;
import com.mickeywilliamson.project8.Models.Supplement;
import com.mickeywilliamson.project8.Models.ProtocolNonMalignant;

import net.danlew.android.joda.JodaTimeAndroid;

public class DailyScheduleActivity extends AppCompatActivity implements
        HourlyTaskDialogFragment.HourlyTaskDialogListener,
        DatePickerFragment.OnFragmentInteractionListener {

    private LocalDate todayDate;
    private String chosenDate;
    private static final int NUM_SCHEDULES = 10;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        JodaTimeAndroid.init(this);

        // Get current moment in default time zone
        DateTime dt = new DateTime();
        // Translate to user's local timezone
        DateTime dateTimeLocal = dt.withZone(DateTimeZone.forID(DateTimeZone.forTimeZone(TimeZone.getDefault()).toString()));

        todayDate = new LocalDate(dateTimeLocal.getYear(), dateTimeLocal.getMonthOfYear(), dateTimeLocal.getDayOfMonth());

        if (getIntent().getStringExtra("query_date") != null) {
            Log.d("QUERY DATE", getIntent().getStringExtra("query_date"));
            chosenDate = getIntent().getStringExtra("query_date");
        } else {

            chosenDate = todayDate.toString();
            Log.d("CHOSEN DATE", "CHOSEN DATE");
            setTitle("Today");

        }

        todayDate = new LocalDate();

        //setTitle(chosenDate);

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mPagerAdapter = new DailySchedulePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(5);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 5 && todayDate.toString().equals(chosenDate)) {
                    setTitle("Today");
                } else {
                    setTitle(new LocalDate(chosenDate).plusDays(position - 5).toString());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //setTitle(chosenDate);
        /*DailyScheduleFragment dailyScheduleFragment = new DailyScheduleFragment();
        dailyScheduleFragment.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.daily_schedule_fragment, dailyScheduleFragment.newInstance(1, chosenDate), "DAILYSCHEDULEFRAGMENT");
        fragmentTransaction.commit();*/

    }

    // Returns data from the date picker dialog.
    @Override
    public void onFragmentInteraction(String date) {

        chosenDate = date;

        setTitle(chosenDate);

        /*Bundle bundle = new Bundle();
        bundle.putInt("column-count", 1);
        bundle.putString("CHOSENDATE", date);
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        DailyScheduleFragment newFragment = new DailyScheduleFragment();
        newFragment.setArguments(bundle);
        fragTransaction.replace(R.id.daily_schedule_fragment, newFragment.newInstance(1, date));
        fragTransaction.commit();*/
        mPagerAdapter = new DailySchedulePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(5);
    }

    // Returns data from the Hourly Dialog list of tasks
    @Override
    public void onDialogPositiveClick(Bundle bundle) {

        //DailyScheduleFragment dailyScheduleFragment = (DailyScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.daily_schedule_fragment);
        DailyScheduleFragment dailyScheduleFragment = (DailyScheduleFragment) (mPagerAdapter.instantiateItem(mPager, mPager.getCurrentItem()));

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
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                startActivity(logoutIntent);
                return true;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, UserPreferenceActivity.class);
                startActivity(settingsIntent);
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

    private class DailySchedulePagerAdapter extends FragmentStatePagerAdapter {
        public DailySchedulePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            LocalDate date = new LocalDate(chosenDate).plusDays(position - 5);
            DailyScheduleFragment fragment = new DailyScheduleFragment();
            return fragment.newInstance(1, date.toString());

        }

        @Override
        public int getCount() {
            return NUM_SCHEDULES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 5 && todayDate.toString().equals(chosenDate)) {
                return "Today";
            } else {
                return new LocalDate(chosenDate).plusDays(position - 5).toString();
            }
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
