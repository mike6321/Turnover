package com.joguk.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JoGuk
 * @Since 2018-08-22 오전 11:18.
 * @Description: 첫 Activity에서 띄워줄 PhotoGalleryFragment Class
 */
public class PhotoGalleryFragment extends Fragment {
    // Static Variable
    private RecyclerView mPhotoRecyclerView;
    private static final String TAG = "PhotoGalleryFragment";

    // Member Variable
    private PhotoAdapter mPhotoAdapter;
    private List<GalleryItem> mItems = new ArrayList<>();
    private int mCurrentPageIndex = 0;
    private boolean mNowFetching = false;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);    // orientation이 변경될 때 instance save

        new FetchItemsTask().execute(mCurrentPageIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);       // RecyclerView Inflate

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));   // GridLayout으로 Set

        // Scroll Listener Set
        mPhotoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                GridLayoutManager glm = (GridLayoutManager) recyclerView.getLayoutManager();

//                if (!mPhotoRecyclerView.canScrollVertically(-1)) {  // 세로 스크롤이 가능할 때
//                    if (!mNowFetching) {
//                        Log.i(TAG, "Top bounce Fetching Start!");
//                        mNowFetching = true;
//                        new FetchItemsTask().execute(mCurrentPageIndex = 0);    // 첫페이지 읽기
//                    } else
                {
                    int lastPos = glm.findLastVisibleItemPosition();
                    Log.i(TAG, "lastPos: " + Integer.toString(lastPos));
                    if (lastPos > mItems.size() * 2 / 3) {
                        mCurrentPageIndex++;
                        new FetchItemsTask().execute(mCurrentPageIndex);
                    } else {
                        int firstPos = glm.findFirstVisibleItemPosition();
                        if (firstPos <= 0) {
                            if (!mNowFetching) {
                                Log.i(TAG, "Top bounce Fetching Start!");
                                mNowFetching = true;
                                new FetchItemsTask().execute(mCurrentPageIndex = 0);    // 첫페이지 읽기
                            }
                        }
                    }
                }
            }
        });
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
//            if (mPhotoAdapter != null) {
            //mPhotoAdapter.reset(mItems);
//                mPhotoAdapter.notifyDataSetChanged();
//            } else {
            mPhotoAdapter = new PhotoAdapter(mItems);
            mPhotoRecyclerView.setAdapter(mPhotoAdapter);
//            }
        }
    }

    /**
     * @author JoGuk
     * @Since 2018-08-22 오후 3:11.
     * @Description: PhotoHolder
     */
    private class PhotoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
//        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
//            mItemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        public void bindGalleryItem(GalleryItem item) {
            mTitleTextView.setText(item.toString());
            mTitleTextView.setTextColor(item.isNewlyAdded() ? Color.BLACK : Color.GRAY);
            mTitleTextView.setTypeface(mTitleTextView.getTypeface(), item.isNewlyAdded() ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
        }

//        public void bindDrawable(Drawable drawable) {
//            mItemImageView.setImageDrawable(drawable);
//        }
    }

    /**
     * @author JoGuk
     * @Since 2018-08-22 오후 3:11.
     * @Description: PhotoAdapter
     */
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            View view = inflater.inflate(R.layout.list_item_gallery, viewGroup, false);
//            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
//            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
//            photoHolder.bindDrawable(placeholder);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    // Background Task
    private class FetchItemsTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
        int mPageIndex = 0;

        @Override
        protected List<GalleryItem> doInBackground(Integer... params) {
//            int pageIndex = 0;
            if (params.length > 0) {
                mPageIndex = params[0];
            }

            return new FlickrFetchr().fetchItems(mPageIndex);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            if (mPageIndex > 0 || mItems.size() == 0) {
                for (GalleryItem oldItem : mItems) {
                    oldItem.setNewlyAdded(false);
                }
                for (GalleryItem newItem : items) {
                    newItem.setNewlyAdded(true);
                }
                mItems.addAll(items);
            } else {
                Log.i(TAG, "Top bounce Fetching End!");

                int newlyAddedCount = 0;
                for (int index = 0; index < items.size(); index++) {
                    GalleryItem newItem = items.get(index);
                    boolean newAdded = true;
                    for (GalleryItem oldItem : mItems) {
                        if (oldItem.getId().equals(newItem.getId())) {
                            newAdded = false;
                        }
                    }
                    newItem.setNewlyAdded(newAdded);
                    if (newAdded) newlyAddedCount++;
                }

                if (newlyAddedCount > 0) {
                    Toast.makeText(getActivity(),
                            "Newly Added: " + Integer.toString(newlyAddedCount), Toast.LENGTH_LONG)
                            .show();
                }

                mItems.clear();
                mItems.addAll(items);
            }
            setupAdapter();
            mNowFetching = false;
        }
    }
}
