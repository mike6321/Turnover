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
import android.widget.Button;

import java.util.UUID;

public class CrimeDetailViewFragment extends Fragment{
    private RecyclerView mCrimeRecyclerView;

    private enum CellIndex{
        CellIndex_Header,
        CellIndex_Detail,
        CellIndex_Date,
        CellIndex_Time,
        CellIndex_ChooseSuspect,
        CellIndex_SendReport,
        CellIndex_Solved,
        CellIndex_Button
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    private class ButtonHolder extends RecyclerView.ViewHolder {
        public ButtonHolder(View view){
            super(view);
        }

        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup){
            RecyclerView.ViewHolder holder = null;
//            CellIndex viewType = CellIndex.values()[i];
            CellIndex viewType = CellIndex.values()[1];
            switch(viewType){
                case CellIndex_Header:
                    break;
                case CellIndex_Date:
                case CellIndex_Time:
                case CellIndex_ChooseSuspect:
                case CellIndex_SendReport:
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater.inflate(R.layout.button_view, viewGroup, false);
                    holder = new ButtonHolder(view);
                    break;
                default:
                    break;
            }
            return holder;
        }

        public void onBindViewHolder(){

        }


    }
}
