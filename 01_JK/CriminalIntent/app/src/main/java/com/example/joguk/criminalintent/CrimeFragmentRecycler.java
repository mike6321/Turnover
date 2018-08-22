package com.example.joguk.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeFragmentRecycler extends Fragment {
    // Static Variable
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_PHOTO = 3;
    private static final int REQUEST_DELETE_CONFIRM = 4;

    // Member Variable
    private Crime mCrime;
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;

    public enum ViewType {
        ViewType_TitleHeader, ViewType_GroupHeader, ViewType_Button, ViewType_Checkbox, ViewType_Null
    }

    public enum CellIndex {
        // photo, camera    Title header, title edit
        CellIndex_TitleHeader("", ViewType.ViewType_TitleHeader), CellIndex_DetailsHeader("Detail", ViewType.ViewType_GroupHeader) // details & bottom border
        , CellIndex_Date("Date", ViewType.ViewType_Button), CellIndex_Time("Time", ViewType.ViewType_Button), CellIndex_Solved("Solved", ViewType.ViewType_Checkbox)    // checkbox
        , CellIndex_ChooseSuspect("Choose Suspect", ViewType.ViewType_Button), CellIndex_SendReport("Send Crime Report", ViewType.ViewType_Button), CellIndex_Count("", ViewType.ViewType_Null);

        private final String title;
        private final ViewType viewType;

        CellIndex(String title, ViewType viewType) {
            this.title = title;
            this.viewType = viewType;
        }

        public String getTitle() {
            return title;
        }

        public ViewType getViewType() {
            return viewType;
        }
    }

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
        mRecyclerView.setAdapter(mAdapter = new CrimeAdapter());

        return v;
    }

    private void updatePhotoView(File photoFile, ImageView photoView) {
        if (photoFile == null || !photoFile.exists()) {
            photoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());
            photoView.setImageBitmap(bitmap);
        }
    }

    // ViewHolders
    private class ButtonHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        public ButtonHolder(View itemView) {
            super(itemView);
            mButton = (Button) itemView;
        }

        public Button bindButton(String title) {
            mButton.setText(title);
            return mButton;
        }
    }

    private class SwitchHolder extends RecyclerView.ViewHolder {
        private Switch mButton;

        public SwitchHolder(View itemView) {
            super(itemView);
            mButton = (Switch) itemView;
        }

        public void bindSwitch(String title, boolean checked) {
            mButton.setText(title);
            mButton.setChecked(checked);
        }
    }

    private class GroupHeaderHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public GroupHeaderHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void bindText(String title) {
            mTextView.setText(title);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        private EditText mTitleEdit;
        private ImageView mPhotoButton;
        private ImageButton mCameraButton;
        private File mPhotoFile;

        public HeaderHolder(View itemView) {
            super(itemView);
            mTitleEdit = (EditText) itemView.findViewById(R.id.crime_title);
            mPhotoButton = (ImageView) itemView.findViewById(R.id.crime_photo);
            mCameraButton = (ImageButton) itemView.findViewById(R.id.crime_camera);
            final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mPhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = FileProvider.getUriForFile(getActivity(), "com.example.joguk.criminalintent.fileprovider", mPhotoFile);
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                    for (ResolveInfo activity : cameraActivities) {
                        getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    startActivityForResult(captureImage, REQUEST_PHOTO);
                }
            });
        }

        public void bindHeader(Crime crime) {
            mTitleEdit.setText(crime.getTitle());
            updatePhotoView(mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(crime), mPhotoButton);
        }
    }

    // ViewAdaptor
    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            CellIndex cell = CellIndex.values()[i];
            switch (cell) {
                case CellIndex_TitleHeader: {
                    HeaderHolder h = (HeaderHolder) holder;
                    h.bindHeader(mCrime);
                }
                break;
                case CellIndex_DetailsHeader: {
                    GroupHeaderHolder gh = (GroupHeaderHolder) holder;
                    gh.bindText(cell.getTitle());
                }
                break;
                case CellIndex_Date: {
                    ButtonHolder buttonHolder = (ButtonHolder) holder;

                    Button button = buttonHolder.bindButton(Crime.getDateString(mCrime.getDate()));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager manager = getFragmentManager();
                            DatePickerFragment dialog = DatePickerFragment
                                    .newInstance(mCrime.getDate());
                            dialog.setTargetFragment(CrimeFragmentRecycler.this, REQUEST_DATE);
                            dialog.show(manager, DIALOG_DATE);
                        }
                    });
                }
                break;
                case CellIndex_Time: {
                    ButtonHolder buttonHolder = (ButtonHolder) holder;

                    Button button = buttonHolder.bindButton(Crime.getTimeString(mCrime.getDate()));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager manager = getFragmentManager();
                            TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                            dialog.setTargetFragment(CrimeFragmentRecycler.this, REQUEST_TIME);
                            dialog.show(manager, DIALOG_DATE);
                        }
                    });
                }
                break;
                case CellIndex_ChooseSuspect:
                case CellIndex_SendReport: {
                    ButtonHolder buttonHolder = (ButtonHolder) holder;
                    buttonHolder.bindButton(cell.getTitle());
                }
                break;
                case CellIndex_Solved: {
                    SwitchHolder switchHolder = (SwitchHolder) holder;
                    switchHolder.bindSwitch(cell.getTitle(), mCrime.isSolved());
                }
                break;

                default:
                    break;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder holder = null;
            ViewType viewType = ViewType.values()[i];
            switch (viewType) {
                case ViewType_TitleHeader: {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater
                            .inflate(R.layout.title_header_cell, viewGroup, false);
//                    View v = view.findViewById(R.id.group_header_cell_id);
                    holder = new HeaderHolder(view);
                }
                break;
                case ViewType_GroupHeader: {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater
                            .inflate(R.layout.group_header_cell, viewGroup, false);
                    View v = view.findViewById(R.id.group_header_cell_id);
                    holder = new GroupHeaderHolder(v);
                }
                break;
                case ViewType_Button: {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater
                            .inflate(R.layout.button_cell, viewGroup, false);
                    View v = view.findViewById(R.id.button_cell_widget);
                    holder = new ButtonHolder(v);
                }
                break;
                case ViewType_Checkbox: {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater
                            .inflate(R.layout.switch_cell, viewGroup, false);
                    View v = view.findViewById(R.id.switch_cell_id);
                    holder = new SwitchHolder(v);
                }
                break;
                default: {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view = layoutInflater
                            .inflate(R.layout.button_cell, viewGroup, false);
                    View v = view.findViewById(R.id.button_cell_widget);
                    holder = new ButtonHolder(v);
                }
                break;
            }

            return holder;
        }

        @Override
        public int getItemCount() {
            return CellIndex.CellIndex_Count.ordinal();
        }

        @Override
        public int getItemViewType(int position) {
            CellIndex cellIndex = CellIndex.values()[position];
            return cellIndex.getViewType().ordinal();
        }
    }

    private void updateCrime() {
        // TODO: not implemented yet
        CrimeLab.get(getActivity()).updateCrime(mCrime);
//        mCallbacks.onCrimeUpdated(mCrime);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_DATE:
                if (data == null) {
                    return;
                }
                Date date = (Date) data
                        .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(date);
                mAdapter.notifyDataSetChanged();
//                updateCrime();

                break;
            case REQUEST_TIME:
                if (data == null) {
                    return;
                }
                date = (Date) data
                        .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mCrime.setDate(date);
                mAdapter.notifyDataSetChanged();
//                updateCrime();
                break;
//            case REQUEST_CONTACT:
//                if (data == null) {
//                    return;
//                }
//                Uri contactUri = data.getData();
//                // Specify which fields you want your query to return
//                // values for
//                String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
//                // Perform your query - the contactUri is like a "where"
//                // clause here
//                Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
//                try {
//                    // Double-check that you actually got results
//                    if (c.getCount() == 0) {
//                        return;
//                    }
//                    // Pull out the first column of the first row of data
//                    // that is your suspect's name
//                    c.moveToFirst();
//                    String suspect = c.getString(0);
//                    mCrime.setSuspect(suspect);
//                    mSuspectButton.setText(suspect);
//                    updateCrime();
//                } finally {
//                    c.close();
//                }
//                break;
//            case REQUEST_PHOTO:
//                if (data == null) {
//                    return;
//                }
//                Uri uri = FileProvider.getUriForFile(getActivity(), "com.example.joguk.criminalintent.fileprovider", mPhotoFile);
//                getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                updatePhotoView();
//                updateCrime();
//                break;
//
//            case REQUEST_DELETE_CONFIRM:
//                CrimeLab lab = CrimeLab.get(this.getContext());
//                lab.deleteCrime(mCrime.getId());
//
//                if (mPhotoFile == null || !mPhotoFile.exists()) {
//                } else {
//                    mPhotoFile.delete();
//                }
//
//                if (getActivity() instanceof CrimePagerActivity) {
//                    getActivity().finish();
//                } else if (getActivity() instanceof CrimeListActivity) {
//                    CrimeListFragment parent = (CrimeListFragment) getFragmentManager().getFragments().get(0);
//                    parent.applyCrimeDeleted();
//                }
//                break;
            default:
                break;
        }
    }
}
