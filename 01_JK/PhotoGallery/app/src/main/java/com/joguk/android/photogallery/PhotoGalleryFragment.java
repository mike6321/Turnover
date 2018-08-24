package com.joguk.android.photogallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JoGuk
 * @Since 2018-08-23 오후 4:19.
 * @Description: 첫 액티비티에서 그릴 Gallery Fragment
 */
public class PhotoGalleryFragment extends Fragment {
    // Static variable
    private static final String TAG = "PhotoGalleryFragment";
    private static final int MAX_ITEM_COUNT = 1024;

    // Member Variable
    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private List<GalleryItem> mItems = new ArrayList<>();
    private int mCurrentPageIndex = 0;
    private boolean mNowFetching = false;
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        updateItems();

        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                photoHolder.bindDrawable(drawable, drawable.toString());
            }
        });
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManagerEx(getActivity(), 3));

        mPhotoRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        setupAdapter();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                QueryPreferences.setStoredQuery(getActivity(), s);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateItems() {
        String query = QueryPreferences.getStoredQuery(getActivity());
        new FetchItemsTask(query).execute(mCurrentPageIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoAdapter = new PhotoAdapter(mItems);
            mPhotoRecyclerView.setAdapter(mPhotoAdapter);
        }
    }

    /**
     * @author JoGuk
     * @Since 2018-08-23 오전 10:11.
     * @Description:
     */
    private class GridLayoutManagerEx extends GridLayoutManager {
        GridLayoutManagerEx(Context context, int spanCount) {
            super(context, spanCount);
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

            int scrollRange = super.scrollVerticallyBy(dy, recycler, state);
            if (!mNowFetching) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                int overscroll = dy - scrollRange;
                if (overscroll > 0) {
                    // bottom overscroll
                    Log.i(TAG, "bottom overscroll: " + Integer.toString(dy));
                    mNowFetching = true;
                    mCurrentPageIndex++;
                    new FetchItemsTask(query).execute(mCurrentPageIndex);
                } else if (overscroll < -50) {
                    // top overscroll
                    Log.i(TAG, "top overscroll: " + Integer.toString(dy));
                    mNowFetching = true;
                    new FetchItemsTask(query).execute(mCurrentPageIndex = 0);
                }
            }
            return scrollRange;
        }

        @Override
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return 480;
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImageView;
        private TextView mItemTextView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            mItemTextView = (TextView) itemView.findViewById(R.id.item_text_view);
        }

        public void bindDrawable(Drawable drawable, String title) {
            if (drawable != null) {
                mItemImageView.setImageDrawable(drawable);
                mItemImageView.setVisibility(View.VISIBLE);
                mItemTextView.setVisibility(View.INVISIBLE);
            } else {
                mItemTextView.setText(title);
                mItemTextView.setVisibility(View.VISIBLE);
                mItemImageView.setVisibility(View.INVISIBLE);
            }
        }

        //                    item.isNewlyAdded() ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
        //            mTitleTextView.setTypeface(mTitleTextView.getTypeface(),
        //                            Color.GRAY);
        //                            Color.BLACK :
        //                    (item.isNewlyAdded() ?
        //            mTitleTextView.setTextColor
        //            mTitleTextView.setText(item.toString());
//        public void bindGalleryItem(GalleryItem item) {

//        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public void reset(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            TextView textView = new TextView(getActivity());
//            return new PhotoHolder(textView);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_gallery, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
//            photoHolder.bindGalleryItem(galleryItem);
            GalleryItem galleryItem = mGalleryItems.get(position);
            Drawable placeholder = getResources().getDrawable(R.drawable.ic_hyeri);
            photoHolder.bindDrawable(null, galleryItem.getId());

            mThumbnailDownloader.queueThumbnail(photoHolder, galleryItem.getUrl());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
        private int mPageIndex = 0;
        private String mQuery;

        public FetchItemsTask(String query) {
            mQuery = query;
        }

        @Override
        protected List<GalleryItem> doInBackground(Integer... params) {
            if (params.length > 0) {
                mPageIndex = params[0];
            }

            if (mQuery == null) {
                return new FlickrFetchr().fetchRecentPhotos(mPageIndex);
            } else {
                return new FlickrFetchr().searchPhotos(mQuery, mPageIndex);
            }

//            return new FlickrFetchr().fetchItems(mPageIndex);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            if (mCurrentPageIndex > 0 || mItems.size() == 0) {
                // 처음 읽을 때
                if (mItems.size() == 0) {
                    mItems.addAll(items);
                    mPhotoAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, "Bottom bounce Fetching End!");
                    for (GalleryItem oldItem : mItems) {
                        oldItem.setNewlyAdded(false);
                    }
                    for (GalleryItem newItem : items) {
                        newItem.setNewlyAdded(true);
                    }
                    int oldSize = mItems.size();
                    mItems.addAll(items);
                    mPhotoAdapter.notifyItemRangeChanged(0, oldSize);
                    mPhotoAdapter.notifyItemRangeInserted(oldSize, items.size());
//                    int scrollPos = ;
//                    mPhotoRecyclerView.smoothScrollToPosition();
                }
            } else {
                Log.i(TAG, "Top bounce Fetching End!");

                ArrayList<GalleryItem> news = new ArrayList<>();

                for (GalleryItem newItem : items) {
                    boolean newlyMade = true;
                    // old Item's newlyMade set false
                    for (GalleryItem oldItem : mItems) {
                        if (oldItem.getId().equals(newItem.getId())) {
                            newlyMade = false;
                            break;
                        }
                    }
                    if (newlyMade) {
                        newItem.setNewlyAdded(true);
                        news.add(newItem);
                    }
                }

                // duplication check
//                ArrayList<GalleryItem> dupCheckItems = new ArrayList<>();   // newItems 중 중복이 아닌 것들만 add한 List
//                for(GalleryItem newItem : news) {
//                    boolean isDuplicate = false;
//                    for(GalleryItem oldItem : items) {
//                        if(newItem.getId().equals(oldItem.getId())){
//                            isDuplicate = true;
//                            break;
//                        }
//                    }
//                    // 중복이 아닌 것들만 Add
//                    if(!isDuplicate){
//                        newItem.setNewlyAdded(true);
//                        dupCheckItems.add(newItem);
//                    }
//                }

                // new item exists
                if (news.size() > 0) {
                    ArrayList<GalleryItem> olds = new ArrayList<>();
                    for (int i = 0; i < mItems.size() && i < MAX_ITEM_COUNT - news.size(); i++) {
                        GalleryItem oldItem = mItems.get(i);
                        oldItem.setNewlyAdded(false);
                        olds.add(oldItem);
                    }
                    int deletedCount = Math.max(0, mItems.size() - olds.size());
                    mItems.clear();
                    mItems.addAll(news);
                    mItems.addAll(olds);
//                    mPhotoAdapter.notifyDataSetChanged();
                    mPhotoAdapter.notifyItemRangeChanged(news.size(), olds.size());
                    mPhotoAdapter.notifyItemRangeInserted(0, news.size());
                    if (deletedCount > 0) {
                        mPhotoAdapter.notifyItemRangeRemoved(mItems.size(), deletedCount);
                    }
                    mPhotoRecyclerView.smoothScrollToPosition(0);
                }
            }

            mNowFetching = false;
        }
    }
}
