package com.joguk.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JoGuk
 * @Since 2018-08-22 오전 11:18.
 *
 * @Description: Flickr에서 JSON으로 파싱하여 가져올 FlickrFetchr Class
 */
public class FlickrFetchr {
    // Static Variable
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "c38765a919a3c803c2101cce32340104";
    private static final String API_PASSWORD = "6376e42b847e34c1";

    private int mTotalPages = 10;

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    /**
     * @author JoGuk
     * @Since 2018-08-22 오전 10:35.
     * @Description: Flickr API get
     */
    public List<GalleryItem> fetchItems(int pageIndex){
        List<GalleryItem> items = new ArrayList<>();

        if (pageIndex < mTotalPages) {
            String page = Integer.toString(pageIndex + 1);
            try {
                String url = Uri.parse("https://api.flickr.com/services/rest/")
                        .buildUpon()
                        .appendQueryParameter("method", "flickr.photos.getRecent")
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("format", "json")
                        .appendQueryParameter("nojsoncallback", "1")
                        .appendQueryParameter("extras", "url_s")
                        .appendQueryParameter("page", page)
                        .build().toString();
                String jsonString = getUrlString(url);

//                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);
                parseItems(items, jsonBody);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch items", ioe);
            } catch (JSONException je){
                Log.e(TAG, "JSONException", je);
            }
        }

        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        mTotalPages = photosJsonObject.getInt("pages");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        // Json Length만큼 Model에 Set
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));
            if (!photoJsonObject.has("url_s")) {
                continue;
            }
            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }
}
