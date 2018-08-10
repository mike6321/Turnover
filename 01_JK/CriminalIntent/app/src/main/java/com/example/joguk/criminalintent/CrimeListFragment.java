package com.example.joguk.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

public class CrimeListFragment extends Fragment {
    private static final int REQUEST_CRIME = 1;
    private final String TAG = "ListFragment";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private UUID mDetailId;
    private int mLastPagerPosition = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayoutManager manager = (LinearLayoutManager)mCrimeRecyclerView.getLayoutManager();
        int firstPos = manager.findFirstCompletelyVisibleItemPosition();
        int lastPos = manager.findLastCompletelyVisibleItemPosition();
        if (mLastPagerPosition >= 0 && firstPos < lastPos) {
            if (mLastPagerPosition < firstPos) {
                manager.scrollToPosition(mLastPagerPosition);
            } else if (mLastPagerPosition > lastPos) {
                manager.scrollToPosition(mLastPagerPosition);
            }
        }
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            // mDetailId
            mAdapter.notifyDataSetChanged();
//            int position = -1;
//            for (int ndx = 0; ndx < crimes.size(); ndx++) {
//                Crime oneCrime = crimes.get(ndx);
//                if (oneCrime.getId() == mDetailId) {
//                    position = ndx;
//                    break;
//                }
//            }
//            if (position >= 0) {
//                mAdapter.notifyItemChanged(position);
//            } else {
//                mAdapter.notifyDataSetChanged();
//            }
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }
        public void bind(Crime crime) {
            Log.d(TAG, crime.getTitle());
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mTitleTextView.setTextColor(crime.isSolved() ? Color.rgb(180,180,180) : Color.rgb(0,0,0));
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }
        @Override
        public void onClick(View view) {
            mDetailId = mCrime.getId();
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        int position = CrimePagerActivity.getCurrentPosition(data);
        Log.d(TAG, "onActivityResult: " + Integer.toString(position));

        mLastPagerPosition = position;
        if (requestCode == REQUEST_CRIME) {
            // Handle result
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
