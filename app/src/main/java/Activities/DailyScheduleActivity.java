package Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.buildware.widget.indeterm.IndeterminateCheckBox;
import com.facebook.login.LoginManager;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import Fragments.DatePickerFragment;
import Fragments.HourlyTaskDialogFragment;
import Models.CeCoe;
import Models.Hour;
import Models.Juice;
import Models.Meal;
import Models.Supplement;
import Models.Task;
import Models.ProtocolNonMalignant;

public class DailyScheduleActivity extends AppCompatActivity implements
        HourlyTaskDialogFragment.HourlyTaskDialogListener,
        DatePickerFragment.OnFragmentInteractionListener {

    private DatabaseReference mDatabase;
    private RecyclerView mProtocolRv;
    private String protocolKey;
    private LocalDate chosenDate;
    private ProtocolRecyclerViewAdapter mProtocolAdapter;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        if (chosenDate == null) {
            chosenDate = new LocalDate();
        }

        protocolKey = "daily/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "_";
        path = protocolKey + chosenDate;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*SnapshotParser<Hour> snapShotParser = new SnapshotParser<Hour>() {
            @NonNull
            @Override
            public Hour parseSnapshot(@NonNull DataSnapshot snapshot) {


                Hour hmm = snapshot.getValue(Hour.class);
                Log.d("WELL", hmm.getJuice().toString());
                return hmm;
            }
        };*/

        Query query = mDatabase.child(path);

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();

        mProtocolRv = findViewById(R.id.rv_protocol);
        mProtocolRv.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mProtocolRv.setLayoutManager(mLayoutManager);
        mProtocolAdapter = new ProtocolRecyclerViewAdapter(this, mDatabase, path, options);
        mProtocolRv.setAdapter(mProtocolAdapter);
    }

    @Override
    public void onFragmentInteraction(String date) {

        chosenDate = new LocalDate(date);
        path = protocolKey + chosenDate;
        Query query = mDatabase.child(path);

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();
        final FirebaseRecyclerAdapter oldAdapter = (FirebaseRecyclerAdapter) mProtocolRv.getAdapter();
        mProtocolAdapter = new ProtocolRecyclerViewAdapter(this, mDatabase, path, options);
        mProtocolRv.setAdapter(mProtocolAdapter);
        mProtocolAdapter.notifyDataSetChanged();
        if (oldAdapter != null) {
            //oldAdapter.cleanup(); - This can't run here because cleanup is not public so not sure what to do with it!
        }
    }

    private void loadData(LocalDate date) {

    }

    public static class ProtocolRecyclerViewAdapter extends FirebaseRecyclerAdapter<Hour, ProtocolRecyclerViewAdapter.HourHolder> {

        private final DailyScheduleActivity mParentActivity;
        private DatabaseReference mDb;
        private String protocolUserDateKey;
        ProtocolNonMalignant mProtocol = new ProtocolNonMalignant();


        ProtocolRecyclerViewAdapter(DailyScheduleActivity parent, DatabaseReference db, String path, FirebaseRecyclerOptions<Hour> options) {
            super(options);
            mParentActivity = parent;
            mDb = db;
            protocolUserDateKey = path;
        }

        @Override
        public void onDataChanged() {
            super.onDataChanged();

        }

        @NonNull
        @Override
        public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_protocol_row, parent, false);
            return new HourHolder(view);
        }

        @Override
        public void onBindViewHolder(final @NonNull HourHolder holder, final int position, final Hour currentHour) {

            holder.mHourCheckBox.setText(currentHour.toString() + protocolUserDateKey);

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

            //https://stackoverflow.com/questions/25646048/how-to-convert-local-time-to-am-pm-time-format-using-jodatime
            LocalTime time = new LocalTime(currentHour.getMilitaryHour(), 0);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm a");
            holder.mHour.setText(fmt.print(time));

            holder.mHourCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {

                    if (isChecked) {
                        currentHour.setAllCompleted(true);
                        mDb.child(protocolUserDateKey).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);
                    } else {
                        currentHour.setAllCompleted(false);
                        mDb.child(protocolUserDateKey).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);
                        //holder.mHourCheckBox.setChecked(false);
                    }
                }
            });

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
            ArrayList<Supplement> supplements = hour.getSupplements();
            for (int i = 0; i < supplements.size(); i++) {
                if (supplements.get(i).isCompleted() == true) {
                    allFalse = false;
                } else {
                    allTrue = false;
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

        @Override
        public int getItemCount() {

/*
            if (getSnapshots().size() == 0) {
                ProtocolNonMalignant mProtocol = new ProtocolNonMalignant();
                ArrayList<Hour> hours = mProtocol.buildProtocol();
                for (int i = 0; i < hours.size(); i++) {
                    mDb.child(protocolUserDateKey).child(String.valueOf(hours.get(i).getMilitaryHour())).setValue(hours.get(i));
                }
            } else {
                // Populate the db if the current day doesn't yet exist for the current user.

                return getSnapshots().size();

            }*/
            return super.getItemCount();

        }
    }

    @Override
    public void onDialogPositiveClick(Bundle bundle) {

        Hour hour = bundle.getParcelable("hour");

        mDatabase.child(path).child(String.valueOf(hour.getMilitaryHour())).setValue(hour);
        mProtocolAdapter.notifyDataSetChanged();

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
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "date picker");
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mProtocolAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mProtocolAdapter.stopListening();
    }
}
