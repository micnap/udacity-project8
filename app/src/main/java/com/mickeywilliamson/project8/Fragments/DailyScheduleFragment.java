package com.mickeywilliamson.project8.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mickeywilliamson.project8.Activities.DailyScheduleActivity;
import com.mickeywilliamson.project8.Models.Hour;
import com.mickeywilliamson.project8.Adapters.ProtocolRecyclerViewAdapter;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class DailyScheduleFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private String protocolKey;
    private DatabaseReference mDatabase;
    private RecyclerView mProtocolRv;
    private String chosenDate;
    private ProtocolRecyclerViewAdapter mProtocolAdapter;
    private String path;
    private boolean reset;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DailyScheduleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DailyScheduleFragment newInstance(int columnCount, String chosenDateString) {
        DailyScheduleFragment fragment = new DailyScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("CHOSENDATE", chosenDateString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            chosenDate = getArguments().getString("CHOSENDATE");
            Log.d("CHOSENDATE", chosenDate);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        protocolKey = "daily/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "_";
        path = protocolKey + new LocalDate(chosenDate);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        /*SnapshotParser<Hour> snapShotParser = new SnapshotParser<Hour>() {
            @NonNull
            @Override
            public Hour parseSnapshot(@NonNull DataSnapshot snapshot) {

                Hour hmm = snapshot.getValue(Hour.class);
                return hmm;
            }
        };*/


        Query query = mDatabase.child(path);

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();


        mProtocolRv.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mProtocolRv.setLayoutManager(mLayoutManager);
        mProtocolAdapter = new ProtocolRecyclerViewAdapter((DailyScheduleActivity) getActivity(), mDatabase, path, options);
        Log.d("PATH", path);
        mProtocolRv.setAdapter(mProtocolAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_schedule, container, false);
        mProtocolRv = view.findViewById(R.id.rv_protocol);



        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mProtocolAdapter = null;

    }

    public void reload(Bundle bundle) {
        Hour hour = bundle.getParcelable("hour");
        mDatabase.child(path).child(String.valueOf(hour.getMilitaryHour())).setValue(hour);

        /*Query query = mDatabase.child(path);

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();

        mProtocolAdapter = new ProtocolRecyclerViewAdapter((DailyScheduleActivity) getActivity(), mDatabase, path, options);
        mProtocolRv.setAdapter(mProtocolAdapter);*/


        //mProtocolAdapter.notifyDataSetChanged();
    }

    public void reload2(String date) {


        path = protocolKey + new LocalDate(date);
        Query query = mDatabase.child(path);

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();

        mProtocolAdapter = new ProtocolRecyclerViewAdapter((DailyScheduleActivity) getActivity(), mDatabase, path, options);
        mProtocolRv.setAdapter(mProtocolAdapter);


        mProtocolAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        mProtocolAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mProtocolAdapter.stopListening();
    }
}
