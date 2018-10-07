package com.mickeywilliamson.project8.Fragments;

import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import android.os.Bundle;

import com.mickeywilliamson.project8.Misc.DatePreference;
import com.mickeywilliamson.project8.Misc.ExtendedMultiSelectListPreference;
import com.mickeywilliamson.project8.R;

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

        final MultiSelectListPreference mslPref = (MultiSelectListPreference) findPreference("pref_daily_schedule");
        mslPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                ExtendedMultiSelectListPreference emslPreference = (ExtendedMultiSelectListPreference) preference;
                emslPreference.setSummary(getResources(), o);
                return true;
            }
        });

        final DatePreference dpPref = (DatePreference) findPreference("pref_start_date");

        dpPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                //your code to change values.
                dpPref.setSummary((String) newValue);
                return true;
            }
        });
    }
}
