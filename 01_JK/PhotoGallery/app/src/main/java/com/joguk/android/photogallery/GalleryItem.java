package com.joguk.android.photogallery;

/**
 * PhotoGallery
 * Class: GalleryItem
 * Created by Joguk on 2018-08-22.
 * <p>
 * Description: Gallery의 Model
 */
public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;
    private boolean mNewlyAdded;

    @Override
    public String toString() { return mCaption; }
    public String getCaption() { return mCaption; }
    public void setCaption(String caption) { mCaption = caption; }
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }
    public String getUrl() { return mUrl; }
    public void setUrl(String url) { mUrl = url; }
    public boolean isNewlyAdded() { return mNewlyAdded; }
    public void setNewlyAdded(boolean newlyAdded) { mNewlyAdded = newlyAdded; }
}
