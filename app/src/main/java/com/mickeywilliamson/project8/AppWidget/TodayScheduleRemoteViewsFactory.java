package com.mickeywilliamson.project8.AppWidget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mickeywilliamson.project8.Models.Hour;
import com.mickeywilliamson.project8.Models.Protocol;
import com.mickeywilliamson.project8.Models.ProtocolChemo;
import com.mickeywilliamson.project8.Models.ProtocolFull;
import com.mickeywilliamson.project8.Models.ProtocolNonMalignant;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TodayScheduleRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Hour> mHours;
    private Context mContext;
    private Protocol mProtocol;

    public TodayScheduleRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;

        ProtocolNonMalignant protocol = new ProtocolNonMalignant();

        mHours = protocol.getSchedule();

        /*
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = auth.getCurrentUser().getUid();
        //mainNode = FirebaseDatabase.getInstance().getReference().child(context.getResources().getString(R.string));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String protocolVersion = prefs.getString("pref_protocol", null);
        if (protocolVersion.equals(Protocol.PROTOCOL_FULL)) {
            mProtocol = new ProtocolFull();
        } else if (protocolVersion.equals(Protocol.PROTOCOL_CHEMO)) {
            mProtocol = new ProtocolChemo();
        } else {
            mProtocol = new ProtocolNonMalignant();
        }
        mHours = mProtocol.getSchedule();*/
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mHours.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.lv_protocol_row);
        Hour hour = mHours.get(i);

        LocalTime time = new LocalTime(hour.getMilitaryHour(), 0);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm a");
        remoteViews.setTextViewText(R.id.hour, fmt.print(time));

        Set<String> showHour = new HashSet<>();
        showHour.add("juices");
        showHour.add("meals");
        showHour.add("ces");
        showHour.add("supps");

        remoteViews.setTextViewText(R.id.tasks, hour.toString(showHour));

        Intent fillInIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_row, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
