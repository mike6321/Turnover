package com.example.joguk.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ActionConfirmFragment extends DialogFragment {
    public static final String EXTRA_CONFIRMED = "com.example.joguk.android.criminalintent.confirm";
    public static final String ARG_MESSAGE = "message";

    // create newInstance
    public static ActionConfirmFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MESSAGE, message);
        ActionConfirmFragment fragment = new ActionConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        CharSequence msg = (String) getArguments().getSerializable(ARG_MESSAGE);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(getTargetFragment() == null) {
                                    return;
                                }
//                                getTargetFragment().onActivityResult();

                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONFIRMED, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
