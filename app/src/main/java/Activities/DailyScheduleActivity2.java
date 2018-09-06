package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import Fragments.HourlyTaskDialogFragment;
import Models.Hour;
import Models.ProtocolNonMalignant;
import Models.Supplement;

public class DailyScheduleActivity2 extends AppCompatActivity implements HourlyTaskDialogFragment.HourlyTaskDialogListener  {

    private DatabaseReference mDatabase;
    private RecyclerView mProtocolRv;
    private String protocolKey;
    private ProtocolRecyclerViewAdapter mProtocolAdapter;
    private ArrayList<Hour> mProtocol;
    private ObservableSnapshotArray<Hour> protocolSnapshots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        protocolKey = "daily/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + new LocalDate();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = mDatabase.child(protocolKey);


        SnapshotParser<Hour> snapShotParser = new SnapshotParser<Hour>() {
            @NonNull
            @Override
            public Hour parseSnapshot(@NonNull DataSnapshot snapshot) {


                Hour hmm = snapshot.getValue(Hour.class);
                return hmm;
            }
        };

        FirebaseRecyclerOptions<Hour> options =
                new FirebaseRecyclerOptions.Builder<Hour>()
                        .setQuery(query, Hour.class)
                        .build();


        mProtocolRv = findViewById(R.id.rv_protocol);
        mProtocolRv.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mProtocolRv.setLayoutManager(mLayoutManager);
        mProtocolAdapter = new ProtocolRecyclerViewAdapter(this, mDatabase, protocolKey, options, mProtocol);
        mProtocolRv.setAdapter(mProtocolAdapter);

    }

    public static class ProtocolRecyclerViewAdapter extends FirebaseRecyclerAdapter<Hour, ProtocolRecyclerViewAdapter.HourHolder> {

        private final DailyScheduleActivity2 mParentActivity;
        private ObservableSnapshotArray protocolSnapshots;

        ProtocolRecyclerViewAdapter(DailyScheduleActivity2 parent, DatabaseReference db, String protocolKey, FirebaseRecyclerOptions<Hour> options, ArrayList<Hour> protocol) {
            super(options);
            protocolSnapshots = options.getSnapshots();
            mParentActivity = parent;
        }

        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
            super.onChildChanged(type, snapshot, newIndex, oldIndex);
            Log.d("BLA", "GRRRRRR");
            Hour thisHour = snapshot.getValue(Hour.class);

            Log.d("THISHOUR" + newIndex, thisHour.toString());
        }



        @NonNull
        @Override
        public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_protocol_row, parent, false);
            return new HourHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HourHolder holder, final int position, final Hour currentHour) {
            if (currentHour.getMilitaryHour() == 8) {
                Log.d("MEAL8", String.valueOf(currentHour.getMeal().getState()));
            }


            holder.mHourCheckBox.setText(currentHour.toString());

            //https://stackoverflow.com/questions/25646048/how-to-convert-local-time-to-am-pm-time-format-using-jodatime
            LocalTime time = new LocalTime(currentHour.getMilitaryHour(), 0);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm a");
            holder.mHour.setText(fmt.print(time));

            holder.mOpenTasks.setOnClickListener(null);
            holder.mOpenTasks.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DialogFragment hourlyTasksFragment = new HourlyTaskDialogFragment().newInstance(currentHour);
                    hourlyTasksFragment.show(mParentActivity.getSupportFragmentManager(), "HourlyTasks");
                }
            });
        }

        class HourHolder extends RecyclerView.ViewHolder {

            final TextView mHourCheckBox;
            final ImageView mOpenTasks;
            final TextView mHour;

            HourHolder(View view) {
                super(view);
                mHourCheckBox = (TextView) view.findViewById(R.id.cb_hour_all);
                mOpenTasks = (ImageView) view.findViewById(R.id.open_tasks);
                mHour = (TextView) view.findViewById(R.id.hour);
            }
        }
    }

    @Override
    public void onDialogPositiveClick(Bundle bundle) {

        final Hour hour = bundle.getParcelable("hour");
        int position = bundle.getInt("position");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDatabase.child(protocolKey).child(String.valueOf(hour.getMilitaryHour())).setValue(hour);
                //mProtocolAdapter.notifyDataSetChanged();
            }
        });


        //mProtocolAdapter.notifyItemChanged(position, hour);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProtocolAdapter.notifyDataSetChanged();
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
