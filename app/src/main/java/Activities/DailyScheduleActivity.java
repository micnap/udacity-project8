package Activities;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickeywilliamson.project8.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import Fragments.HourlyTaskDialogFragment;
import Models.CeCoe;
import Models.Hour;
import Models.Juice;
import Models.Meal;
import Models.Supplement;
import Models.Task;
import Models.ProtocolNonMalignant;

public class DailyScheduleActivity extends AppCompatActivity implements HourlyTaskDialogFragment.HourlyTaskDialogListener  {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private RecyclerView mProtocolRv;
    private ArrayList<Hour> mProtocolSchedule;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mProtocolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Toolbar myToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);

        ProtocolNonMalignant protocol = new ProtocolNonMalignant();
        mProtocolSchedule = protocol.getProtocol();

        mProtocolRv = findViewById(R.id.rv_protocol);
        mProtocolRv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mProtocolRv.setLayoutManager(mLayoutManager);
        mProtocolAdapter = new ProtocolRecyclerViewAdapter(this, mProtocolSchedule, mDatabase, user.getUid());
        mProtocolRv.setAdapter(mProtocolAdapter);



    }


    /**
     * The adapter for the recipe recyclerview.
     */
    public static class ProtocolRecyclerViewAdapter
            extends RecyclerView.Adapter<ProtocolRecyclerViewAdapter.ViewHolder> {

        private final DailyScheduleActivity mParentActivity;
        private ArrayList<Hour> mProtocol;
        private DatabaseReference mDb;
        private String mUid;

        ProtocolRecyclerViewAdapter(DailyScheduleActivity parent, ArrayList<Hour> protocol, DatabaseReference db, String uid) {
            mParentActivity = parent;
            mProtocol = protocol;
            mDb = db;
            mUid = uid;
        }

        @NonNull
        @Override
        public ProtocolRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_protocol_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProtocolRecyclerViewAdapter.ViewHolder holder, final int position) {

            final Hour currentHour = mProtocol.get(position);

            holder.mHourCheckBox.setText(currentHour.toString());

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
            //holder.mHour.setText(String.valueOf(currentHour.getMilitaryHour()));

            holder.mHourCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {

                    cb = (IndeterminateCheckBox) cb;


                    if (cb.isChecked()) {
                        currentHour.setState(true);
                        mDb.child("daily").child(mUid + "_" + new LocalDate()).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);
                        //String classString = task instanceof Supplement ? task.getClassName() + supplementCount : task.getClassName();
                        //mDb.child("daily").child(mUid + "_" + new LocalDate()).child(String.valueOf(currentHour.getMilitaryHour())).child(classString).setValue(task.getType());
                        //if (task instanceof Supplement) {
                        //    supplementCount++;
                        //}
                    } else if(((IndeterminateCheckBox) cb).isIndeterminate()) {
                        mDb.child("daily").child(mUid + "_" + new LocalDate()).child(String.valueOf(currentHour.getMilitaryHour())).setValue(currentHour);

                    } else {
                        currentHour.setState(false);
                        mDb.child("daily").child(mUid + "_" + new LocalDate()).child(String.valueOf(currentHour.getMilitaryHour())).removeValue();
                    }
                }
            });

            holder.mOpenTasks.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DialogFragment hourlyTasksFragment = new HourlyTaskDialogFragment().newInstance(currentHour, position);
                    hourlyTasksFragment.show(mParentActivity.getSupportFragmentManager(), "HourlyTasks");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProtocol.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final IndeterminateCheckBox mHourCheckBox;
            final ImageView mOpenTasks;
            final TextView mHour;

            ViewHolder(View view) {
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
            if (hour.getJuice() != null && hour.getJuice().getState() == true ||
                hour.getMeal() != null && hour.getMeal().getState() == true ||
                hour.getCe() != null && hour.getCe().getState() == true) {
                allFalse = false;
            } else {
                allTrue = false;
            }

            // Check the state of the supplements.
            ArrayList<Supplement> supplements = hour.getSupplements();
            for (int i = 0; i < supplements.size(); i++) {
                if (supplements.get(i).getState() == true) {
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


    }


    @Override
    public void onDialogPositiveClick(Bundle bundle) {
        Hour hour = bundle.getParcelable("hour");
        int hourIndex = bundle.getInt("hourIndex");

        updateDb(hour);
        mProtocolSchedule.set(hourIndex, hour);

        mProtocolAdapter.notifyDataSetChanged();

    }

    private void updateDb(Hour hour) {
        int supplementCount = 0;
        //mDatabase.child("daily").child(user.getUid() + "_" + new LocalDate()).child(String.valueOf(hour.getMilitaryHour())).removeValue();
        mDatabase.child("daily").child(user.getUid() + "_" + new LocalDate()).child(String.valueOf(hour.getMilitaryHour())).setValue(hour);



        //if (task instanceof Supplement) {
        //    supplementCount++;
        //}

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
