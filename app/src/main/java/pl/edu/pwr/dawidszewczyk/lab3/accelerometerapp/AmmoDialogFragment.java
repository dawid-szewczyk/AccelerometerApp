package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;

public class AmmoDialogFragment extends DialogFragment {
    public static final String AMMO_MAX_VALUE_KEY = "ammo_max_value";

    public interface NoticeAmmoDialogListener {
        public void onDialogPositiveClick(int ammoNumber);
        public void onDialogNegativeClick();
    }

    NoticeAmmoDialogListener dialogListener;

    public static AmmoDialogFragment newInstance(int maxValue) {
        AmmoDialogFragment fragment = new AmmoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AMMO_MAX_VALUE_KEY, maxValue);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (NoticeAmmoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(getArguments().getInt(AMMO_MAX_VALUE_KEY, 6));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ammo);
        builder.setMessage(R.string.choose_ammo_number);
        builder.setView(numberPicker);
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.onDialogPositiveClick(numberPicker.getValue());
            }
        });
        builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.onDialogNegativeClick();
            }
        });
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogListener = null;
    }
}
