package com.mickeywilliamson.project8.Misc;

import android.content.Context;
import android.content.res.Resources;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

import com.mickeywilliamson.project8.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ExtendedMultiSelectListPreference extends MultiSelectListPreference {
    public ExtendedMultiSelectListPreference(Context context) {
        super(context);
    }

    public ExtendedMultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ExtendedMultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtendedMultiSelectListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // https://stackoverflow.com/questions/41775332/showing-selected-values-in-summary-from-multiselectlistpreference
    @Override
    public CharSequence getSummary() {
        CharSequence cs = super.getSummary();
        String summary = cs.toString();

        if (summary.contains("%s")) {
            String text = "";
            StringBuilder builder = new StringBuilder();
            CharSequence[] entries = getEntries();
            if(entries.length > 0) {
                CharSequence[] entryValues = getEntryValues();
                Set<String> values = getValues();
                int pos = 0;

                for (String value : values) {
                    pos++;
                    int index = -1;
                    for (int i = 0; i < entryValues.length; i++) {
                        if (entryValues[i].equals(value)) {
                            index = i;
                            break;
                        }
                    }
                    builder.append(entries[index]);
                    if (pos < values.size())
                        builder.append(", ");
                }
                text = builder.toString();
            }
            summary = String.format(summary, text);
        }

        return summary;
    }

    public void setSummary(Resources r, Object o) {
        String[] entries = r.getStringArray(R.array.schedule_entries);
        String[] values = r.getStringArray(R.array.schedule_values);
        HashSet<String> items = (HashSet<String>) o;
        Iterator<String> it = items.iterator();
        String summary = "";
        while (it.hasNext()) {
            String next = it.next();
            for (int i = 0; i < values.length; i++) {
                if (next.equals(values[i])) {
                    if (summary != "") {
                        summary += ", ";
                    }
                    summary += entries[i];
                }
            }
        }
        super.setSummary((CharSequence) summary);
    }

}
