package com.mickeywilliamson.project8.Adapters;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildware.widget.indeterm.IndeterminateCheckBox;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.mickeywilliamson.project8.Activities.DailyScheduleActivity;

import com.mickeywilliamson.project8.Fragments.HourlyTaskDialogFragment;
import com.mickeywilliamson.project8.Models.Hour;
import com.mickeywilliamson.project8.Models.Protocol;
import com.mickeywilliamson.project8.Models.ProtocolChemo;
import com.mickeywilliamson.project8.Models.ProtocolFull;
import com.mickeywilliamson.project8.Models.ProtocolNonMalignant;
import com.mickeywilliamson.project8.Models.Supplement;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class ProtocolRecyclerViewAdapter extends FirebaseRecyclerAdapter<Hour, ProtocolRecyclerViewAdapter.HourHolder> {

    private final DailyScheduleActivity mParentActivity;
    private DatabaseReference mDb;
    private String protocolUserDateKey;
    Protocol mProtocol;
    boolean protocolHasChanged;


    public ProtocolRecyclerViewAdapter(DailyScheduleActivity parent, DatabaseReference db, String path, FirebaseRecyclerOptions<Hour> options, boolean protocolHasChanged) {
        super(options);
        mParentActivity = parent;
        mDb = db;
        protocolUserDateKey = path;
        this.protocolHasChanged = protocolHasChanged;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

        if (getSnapshots().size() == 0 || protocolHasChanged) {
            protocolHasChanged = false;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mParentActivity.getApplicationContext());
            String protocolVersion = prefs.getString("pref_protocol", null);

            if (protocolVersion.equals(Protocol.PROTOCOL_FULL)) {
                mProtocol = new ProtocolFull();
            } else if (protocolVersion.equals(Protocol.PROTOCOL_CHEMO)) {
                mProtocol = new ProtocolChemo();
            } else {
                mProtocol = new ProtocolNonMalignant();
            }

            //mProtocol.buildProtocol();
            ArrayList<Hour> hours = mProtocol.getSchedule();
            for (int i = 0; i < hours.size(); i++) {
                mDb.child(protocolUserDateKey).child(String.valueOf(hours.get(i).getMilitaryHour())).setValue(hours.get(i));
            }
            if (!protocolVersion.equals(Protocol.PROTOCOL_FULL)) {
                mDb.child(protocolUserDateKey).child("600").removeValue();
                mDb.child(protocolUserDateKey).child("930").removeValue();
                mDb.child(protocolUserDateKey).child("1500").removeValue();
                mDb.child(protocolUserDateKey).child("1600").removeValue();
                mDb.child(protocolUserDateKey).child("2200").removeValue();
            }
        }
    }

    @NonNull
    @Override
    public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_protocol_row, parent, false);
        return new HourHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull HourHolder holder, final int position, final Hour currentHour) {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mParentActivity.getApplicationContext());
        Set<String> showMainProtocol = prefs.getStringSet("pref_daily_schedule", new HashSet<String>());
        Log.d("DAILYSCHEDULE", showMainProtocol.toString());

        holder.mHourCheckBox.setText(currentHour.toString(showMainProtocol));

        // Derived from https://stackoverflow.com/questions/25646048/how-to-convert-local-time-to-am-pm-time-format-using-jodatime
        LocalTime time = new LocalTime(currentHour.getMilitaryHour() / 100, currentHour.getMilitaryHour() % 100);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm a");
        holder.mHour.setText(fmt.print(time));

        holder.mHourCheckBox.setOnStateChangedListener(new IndeterminateCheckBox.OnStateChangedListener() {
            @Override
            public void onStateChanged(IndeterminateCheckBox indeterminateCheckBox, @Nullable Boolean state) {
                if (state == null) {  // indeterminate state
                    // do nothing
                } else if (state) {  // checked state
                    currentHour.setAllCompleted(true);
                    mDb.child(protocolUserDateKey).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);
                } else {  // unchecked state
                    currentHour.setAllCompleted(false);
                    mDb.child(protocolUserDateKey).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);
                }
            }
        });

        // Because recyclerview recycles the views, the checked change listener was
        // getting called multiple times when a box was checked.  This corrects that.
        holder.mHourCheckBox.setOnCheckedChangeListener (null);
        String state = determineCheckboxState(currentHour);
        if (state.equals("unchecked")) {
            holder.mHourCheckBox.setChecked(false);

        } else if (state.equals("checked")) {
            holder.mHourCheckBox.setChecked(true);

        } else {
            holder.mHourCheckBox.setIndeterminate(true);

        }

        holder.mOpenTasks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogFragment hourlyTasksFragment = new HourlyTaskDialogFragment().newInstance(currentHour);
                hourlyTasksFragment.show(mParentActivity.getSupportFragmentManager(), "HourlyTasks");
            }
        });
    }

    class HourHolder extends RecyclerView.ViewHolder {

        final IndeterminateCheckBox mHourCheckBox;
        final ImageView mOpenTasks;
        final TextView mHour;

        HourHolder(View view) {
            super(view);
            mHourCheckBox = (IndeterminateCheckBox) view.findViewById(R.id.cb_hour_all);
            mOpenTasks = (ImageView) view.findViewById(R.id.open_tasks);
            mHour = (TextView) view.findViewById(R.id.hour);
        }
    }

    private String determineCheckboxState(Hour hour) {
        boolean allFalse = true;
        boolean allTrue = true;

        // Check the state of Juice, Meal, and CE.
        if (hour.getJuice() != null && hour.getJuice().isCompleted() == true ||
                hour.getMeal() != null && hour.getMeal().isCompleted() == true ||
                hour.getCe() != null && hour.getCe().isCompleted() == true) {
            allFalse = false;
        }

        if (hour.getJuice() != null && hour.getJuice().isCompleted() == false ||
                hour.getMeal() != null && hour.getMeal().isCompleted() == false ||
                hour.getCe() != null && hour.getCe().isCompleted() == false) {
            allTrue = false;
        }

        // Check the state of the supplements.
        if (hour.getSupplements() != null){
            ArrayList<Supplement> supplements = hour.getSupplements();
            for (int i = 0; i < supplements.size(); i++) {
                if (supplements.get(i).isCompleted() == true) {
                    allFalse = false;
                } else {
                    allTrue = false;
                }
            }
        }


        if (allFalse == true) {
            return "unchecked";
        } else if (allTrue == true) {
            return "checked";
        } else {
            return "indeterminant";
        }


    }
/*
    @Override
    public int getItemCount() {
        return getSnapshots().isListening(this) ? getSnapshots().size() : 0;
        //return mSnapshots.isListening(this) ? mSnapshots.size() : 0;
    }*/
}