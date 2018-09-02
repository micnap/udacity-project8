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

import Models.Hour;
import Models.Task;

public class HourlyTaskDialogFragment extends DialogFragment {

    private Hour hour;
    private int hourIndex;
    private String[] tasks;
    private boolean[] checkedTasks;
    HourlyTaskDialogListener mListener;

    public static HourlyTaskDialogFragment newInstance(Hour hour, int hourIndex) {
        HourlyTaskDialogFragment fragment = new HourlyTaskDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("hour", hour);
        bundle.putInt("hourIndex", hourIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        hour = getArguments().getParcelable("hour");
        hourIndex = getArguments().getInt("hourIndex");

        tasks = hour.fetchHourItems();
        checkedTasks = hour.fetchHourItemsState();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setMultiChoiceItems(hour.fetchHourItems(), hour.fetchHourItemsState(),
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
                        hour.updateHourItemsState(checkedTasks);

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("hour", hour);
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
