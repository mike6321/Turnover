package com.example.mesung.mission07;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Mission07Frag extends Fragment {

    private TextView birthDay;
    private EditText name;
    private EditText age;
    private Button save;
    final Calendar cal = Calendar.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mission07_frag, container, false);

        birthDay = (TextView) rootView.findViewById(R.id.birthDay);

        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("생년월일 클릭 : ", "클릭");
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        save = (Button) rootView.findViewById(R.id.save);
        name = (EditText) rootView.findViewById(R.id.name);
        age = (EditText) rootView.findViewById(R.id.age);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("저장 클릭 : ", "클릭");
                Toast.makeText(getActivity(), "이름 : " + name.getText() + " 나이 : "
                        + age.getText() + "생년월일 : " + birthDay.getText(), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
