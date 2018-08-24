package com.joguk.android.photogallery;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * PhotoGallery
 * Class: QueryPreferences
 * Created by Joguk on 2018-08-23.
 * <p>
 * Description: Query Pref Class
 */
public class QueryPreferences {
    // Static Variable
    private static final String PREF_SEARCH_QUERY = "searchQuery";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SEARCH_QUERY, query).apply();
    }
}
