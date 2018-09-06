package Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mickeywilliamson.project8.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DatePickerFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private String chosenDate;

    public DatePickerFragment() {}

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();

        final Calendar c = Calendar.getInstance();
        int year = args.getInt("Year") != 0 ? args.getInt("Year") : c.get(Calendar.YEAR);
        int month = args.getInt("Month") != 0 ? args.getInt("Month") - 1 :  c.get(Calendar.MONTH);
        int day = args.getInt("Day") != 0 ? args.getInt("Day") : c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                chosenDate = view.getYear() + "-" + (view.getMonth()+1) + "-" + view.getDayOfMonth();
                if (mListener != null) {
                    mListener.onFragmentInteraction(chosenDate);
                }
            }
        };

    private DatePickerDialog.OnDismissListener dialogDismissListener = new DatePickerDialog.OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String date);
    }
}
