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

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mickeywilliamson.project8.R;

import java.util.ArrayList;

import Fragments.HourlyTaskDialogFragment;
import Models.Protocol;
import Models.Task;
import Models.ProtocolNonMalignant;

public class DailyScheduleActivity extends AppCompatActivity  implements HourlyTaskDialogFragment.HourlyTaskDialogListener {

    private FirebaseAuth mAuth;
    private RecyclerView mProtocolRv;
    private ArrayList<ArrayList<Task>> mProtocolSchedule;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mProtocolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ProtocolNonMalignant protocol = new ProtocolNonMalignant();
        mProtocolSchedule = protocol.getProtocol();

        mProtocolRv = findViewById(R.id.rv_protocol);
        mProtocolRv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mProtocolRv.setLayoutManager(mLayoutManager);
        mProtocolAdapter = new ProtocolRecyclerViewAdapter(this, mProtocolSchedule);
        mProtocolRv.setAdapter(mProtocolAdapter);

    }


    /**
     * The adapter for the recipe recyclerview.
     */
    public static class ProtocolRecyclerViewAdapter
            extends RecyclerView.Adapter<ProtocolRecyclerViewAdapter.ViewHolder> {

        private final DailyScheduleActivity mParentActivity;
        private ArrayList<ArrayList<Task>> mProtocol;

        ProtocolRecyclerViewAdapter(DailyScheduleActivity parent, ArrayList<ArrayList<Task>> protocol) {
            mParentActivity = parent;
            mProtocol = protocol;
        }

        @NonNull
        @Override
        public ProtocolRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_protocol_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProtocolRecyclerViewAdapter.ViewHolder holder, final int position) {

            final ArrayList<Task> currentHour = mProtocol.get(position);
            String hourlyString = "";

            for (int i = 0; i < currentHour.size(); i++) {
                hourlyString += currentHour.get(i) != null ? currentHour.get(i).toString() + ", " : "";
            }

            holder.mHourCheckBox.setText(hourlyString);

            holder.mHourCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {

                    if (cb.isChecked()) {
                        for (Task task: currentHour) {
                            task.setState(true);
                            Log.d(task.toString(), String.valueOf(task.getState()));
                        }
                    } else {
                        for (Task task: currentHour) {
                            task.setState(false);
                            Log.d(task.toString(), String.valueOf(task.getState()));
                        }
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

            final CheckBox mHourCheckBox;
            final ImageView mOpenTasks;

            ViewHolder(View view) {
                super(view);
                mHourCheckBox = (CheckBox) view.findViewById(R.id.cb_hour_all);
                mOpenTasks = (ImageView) view.findViewById(R.id.open_tasks);
            }
        }
    }

    @Override
    public void onDialogPositiveClick(Bundle bundle) {
        ArrayList<Task> tasks = bundle.getParcelableArrayList("tasks");
        int hourIndex = bundle.getInt("hourIndex");
        mProtocolSchedule.set(hourIndex, tasks);
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
