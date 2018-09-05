package com.mickeywilliamson.project8;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import Activities.DailyScheduleActivity2;
import Fragments.HourlyTaskDialogFragment;
import Models.Grr;
import Models.Hour;

public class GrrActivity extends AppCompatActivity {

    private RecyclerView rv;
    private String protocolKey;
    private DatabaseReference mDatabase;
    private MyAdapter mAdapter;
    private ArrayList<Grr> grrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grr);

        grrList = new ArrayList<>();
        grrList.add(new Grr("test1", "test11"));
        grrList.add(new Grr("test2", "test22"));
        grrList.add(new Grr("test3", "test33"));

        protocolKey = "daily/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + new LocalDate() + "_GRR";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child(protocolKey);


        FirebaseRecyclerOptions<Grr> options =
                new FirebaseRecyclerOptions.Builder<Grr>()
                        .setQuery(query, Grr.class)
                        .build();

        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(this, mDatabase, protocolKey, options, grrList);
        rv.setAdapter(mAdapter);
    }

    public static class MyAdapter extends FirebaseRecyclerAdapter<Grr, MyAdapter.GrrHolder> {

        private final GrrActivity mParentActivity;
        private ArrayList<Grr> list;
        private String pKey;
        private DatabaseReference mDb;

        MyAdapter(GrrActivity parent, DatabaseReference db, String protocolKey, FirebaseRecyclerOptions<Grr> options, ArrayList<Grr> grrList) {
            super(options);
            pKey = protocolKey;
            mParentActivity = parent;
            list = grrList;
            mDb = db;

            Log.d("ITEM COUNT", String.valueOf(getItemCount()));

            if (getItemCount() == 0) {

                for (int i = 0; i < list.size(); i++) {
                    mDb.child(pKey).child(String.valueOf(i)).setValue(list.get(i));
                }
            }
        }

        @Override
        public void onDataChanged() {
            super.onDataChanged();
/*
            // Populate the db if the current day doesn't yet exist for the current user.
            if (getItemCount() == 0) {
                for (int i = 0; i < list.size(); i++) {
                    mDb.child(pKey).child(String.valueOf(i)).setValue(list.get(i));
                }
            }*/
        }


        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
            super.onChildChanged(type, snapshot, newIndex, oldIndex);
            Hour thisHour = snapshot.getValue(Hour.class);
        }

        @NonNull
        @Override
        public GrrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_grr, parent, false);
            return new GrrHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.GrrHolder holder, final int position, final Grr grr) {

            holder.mGrrText.setText(grr.getProperty1() + " " + grr.getProperty2());
/*
            holder.mGrrText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DialogFragment hourlyTasksFragment = new HourlyTaskDialogFragment().newInstance(grr, position);
                    hourlyTasksFragment.show(mParentActivity.getSupportFragmentManager(), "HourlyTasks");
                }
            });*/
        }

        class GrrHolder extends RecyclerView.ViewHolder {

            final TextView mGrrText;

            GrrHolder(View view) {
                super(view);
                mGrrText = (TextView) view.findViewById(R.id.grrText);
            }
        }
    }
}
