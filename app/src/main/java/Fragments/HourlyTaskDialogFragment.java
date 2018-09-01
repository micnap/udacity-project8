package Fragments;

import android.app.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.mickeywilliamson.project8.R;

import java.util.ArrayList;

import Models.Task;

public class HourlyTaskDialogFragment extends DialogFragment {

    private ArrayList<Task> mHourlyTasks;
    private int hourIndex;
    private String[] tasks;
    private boolean[] checkedTasks;
    HourlyTaskDialogListener mListener;

    public static HourlyTaskDialogFragment newInstance(ArrayList<Task> tasks, int hourIndex) {
        HourlyTaskDialogFragment fragment = new HourlyTaskDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("tasks", tasks);
        bundle.putInt("hourIndex", hourIndex);
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mHourlyTasks = getArguments().getParcelableArrayList("tasks");
        hourIndex = getArguments().getInt("hourIndex");
        tasks = new String[mHourlyTasks.size()];
        checkedTasks = new boolean[mHourlyTasks.size()];

        for (int i = 0; i < mHourlyTasks.size(); i++) {
            tasks[i] = mHourlyTasks.get(i).toString();
            checkedTasks[i] = mHourlyTasks.get(i).getState();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setMultiChoiceItems(tasks, checkedTasks,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    checkedTasks[which] = true;
                                } else {
                                    // Else, if the item is already in the array, remove it
                                    checkedTasks[which] = false;
                                }
                            }
                        })

                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < mHourlyTasks.size(); i++) {
                            mHourlyTasks.get(i).setState(checkedTasks[i]);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("tasks", mHourlyTasks);
                        bundle.putInt("hourIndex", hourIndex);

                        mListener.onDialogPositiveClick(bundle);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (HourlyTaskDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement HourlyTaskDialogListener");
        }

    }

    public interface HourlyTaskDialogListener {
        void onDialogPositiveClick(final Bundle bundle);
    }
}
