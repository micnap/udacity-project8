package com.mickeywilliamson.project8.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mickeywilliamson.project8.Activities.UserPreferenceActivity;
import com.mickeywilliamson.project8.Misc.DatePreference;
import com.mickeywilliamson.project8.Misc.ExtendedMultiSelectListPreference;
import com.mickeywilliamson.project8.Models.Protocol;
import com.mickeywilliamson.project8.R;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class UserPreferenceActivityFragment extends PreferenceFragment {

    public UserPreferenceActivityFragment() {}

    // MultiSelectListPreference does not automatically update its summary when the preference is updated.
    // We have to do it manually here.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //scheduleOptionsPref = (ExtendedMultiSelectListPreference) findPreference("pref_")

        final ExtendedMultiSelectListPreference mslPref = (ExtendedMultiSelectListPreference) findPreference("pref_daily_schedule");
        mslPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                mslPref.setSummary(getResources(), o);
                return true;
            }
        });

        final DatePreference startDatePref = (DatePreference) findPreference("pref_start_date");
        startDatePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                //your code to change values.
                startDatePref.setSummary((String) newValue);
                return true;
            }
        });

        final ListPreference protocolPreference = (ListPreference) findPreference("pref_protocol");
        protocolPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                return true;
            }
        });
    }


}
