package com.example.joguk.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {
    // Static Variable
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static final String SAVED_DETAIL_ID = "detail_id";
    private static final int REQUEST_CRIME = 1;
    private final String TAG = "ListFragment";

    // Member Variable
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private int mSelectedIndex = -1;
    private UUID mDetailId;
    private Callbacks mCallbacks;
    private SwipeController mSwipeController;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onCrimeSelected(Crime crime);

        boolean isDetailAvail();
    }

    // 자기를 생성시킬 때 attach(호출)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
            String savedDetailId = savedInstanceState.getString(SAVED_DETAIL_ID);
            if (savedDetailId != null) {
                mDetailId = UUID.fromString(savedDetailId);
            } else {
                mDetailId = null;
            }
        }

        // initailize mDetailId, mSelectedIndex
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mSelectedIndex < 0 || mSelectedIndex >= crimes.size()) {
//        if(mDetailId == null){
            if (crimes.size() > 0) {
                mSelectedIndex = 0;
                Crime crime = crimes.get(0);
                mDetailId = crime.getId();
                if (mCallbacks.isDetailAvail()) {
                    mCallbacks.onCrimeSelected(crime);
                }
            }
        }

        // ItemTouchHelper attach recyclerView
        mSwipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
//                mAdapter.players.remove(position);
                CrimeLab crimeLab = CrimeLab.get(getActivity());
                List<Crime> crimes = crimeLab.getCrimes();
                crimeLab.deleteCrime(crimes.get(position).getId());

//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());

                if (getFragmentManager().getFragments().get(0) instanceof CrimeListFragment) {
                    updateUI();
                } else if (getActivity() instanceof CrimeListActivity) {
                    CrimeListFragment parent = (CrimeListFragment) getFragmentManager().getFragments().get(0);
                    parent.applyCrimeDeleted();
                }
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(mSwipeController);
        itemTouchhelper.attachToRecyclerView(mCrimeRecyclerView);
        mCrimeRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                mSwipeController.onDraw(c);
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayoutManager manager = (LinearLayoutManager) mCrimeRecyclerView.getLayoutManager();
        int firstPos = manager.findFirstCompletelyVisibleItemPosition();
        int lastPos = manager.findLastCompletelyVisibleItemPosition();

        if (mSelectedIndex >= 0 && firstPos < lastPos) {
            if (mSelectedIndex < firstPos) {
                manager.scrollToPosition(mSelectedIndex);
            } else if (mSelectedIndex > lastPos) {
                manager.scrollToPosition(mSelectedIndex);
            }
        }

        // check mDetailId validity
        if (mDetailId != null) {
            CrimeLab crimeLab = CrimeLab.get(getActivity());
            List<Crime> crimes = crimeLab.getCrimes();
            boolean validDetailId = false;
            for (Crime oneCrime : crimes) {
                if (oneCrime.getId().equals(mDetailId)) {
                    validDetailId = true;
                    break;
                }
            }
            if (!validDetailId && crimes.size() > 0) {
                mDetailId = crimes.get(0).getId();
            }
        }

        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
        if (mDetailId != null) {
            outState.putString(SAVED_DETAIL_ID, mDetailId.toString());
        } else {
            outState.putString(SAVED_DETAIL_ID, null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                if (mAdapter != null) {
                    mAdapter.updateCrimeList(CrimeLab.get(getActivity()).getCrimes());
                }

                mDetailId = crime.getId();
                updateUI();
                mCallbacks.onCrimeSelected(crime);

                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    public void applyCrimeDeleted() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        Crime crime = null;
        if (crimes.size() > 0) {
            crime = crimes.get(0);
            mDetailId = crimes.get(0).getId();
        }
        updateUI();
        if (crime != null) {
            mCallbacks.onCrimeSelected(crime);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            mCrime = crime;
            String title;

            if (mCrime.getTitle() == null) {
                title = getString(R.string.no_title);
            } else {
                title = mCrime.getTitle();
            }
            mTitleTextView.setText(title);
            mTitleTextView.setTextColor(crime.isSolved() ? Color.rgb(180, 180, 180) : Color.rgb(0, 0, 0));
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);

            // Click item set Background Color
            if (mCallbacks.isDetailAvail()) {
                itemView.setBackgroundColor((mCrime.getId().equals(mDetailId)) ?
                        Color.rgb(210, 210, 210) :
                        Color.rgb(255, 255, 255)
                );
            }
        }

        @Override
        public void onClick(View view) {
            mDetailId = mCrime.getId();
            mCallbacks.onCrimeSelected(mCrime);

            updateUI();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public void updateCrimeList(List<Crime> crimes) {
//            mCrimes
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

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        int position = CrimePagerActivity.getCurrentPosition(data);
        Log.d(TAG, "onActivityResult: " + Integer.toString(position));

        mSelectedIndex = position;
        if (requestCode == REQUEST_CRIME) {
            // Handle result
        }
    }
}
