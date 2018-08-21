package com.example.joguk.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public class CrimeFragmentRecycler extends Fragment {
    public enum CellIndex{
        // photo, camera, Title header, title edit
        CellIndex_Header(""),
        CellIndex_DetailsHeader("Detail"),
        CellIndex_Date("Date"),
        CellIndex_Time("Time"),
        CellIndex_Solved("Solved"),
        CellIndex_ChooseSuspect("Choose Suspect"),
        CellIndex_SendReport("Send Crime Report"),
        CellIndex_Count("");

        private final String title;

        CellIndex(String title){
            this.title = title;
        }

        public String getTitle() { return title; }
    }

    private static final String ARG_CRIME_ID="crime_id";

    private Crime mCrime;
    private RecyclerView mRecyclerView;

    // create newInstance
    public static CrimeFragmentRecycler newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragmentRecycler fragment = new CrimeFragmentRecycler();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_recycler, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_crime_recycler_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(new Adapter());

        return v;
    }

    
}
