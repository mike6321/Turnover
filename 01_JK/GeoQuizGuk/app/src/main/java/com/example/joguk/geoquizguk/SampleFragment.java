package com.example.joguk.geoquizguk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SampleFragment extends Fragment {

    private int position;

    public static SampleFragment getInstance(int position) {
        SampleFragment sampleFragment = new SampleFragment();
        sampleFragment.position = position;
        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, null);

        // 시작 index가 0이기 때문에 + 1 해준다
//        ((TextView) view.findViewById(R.id.position)).setText(Integer.toString(position+1));

        return view;
    }
}
