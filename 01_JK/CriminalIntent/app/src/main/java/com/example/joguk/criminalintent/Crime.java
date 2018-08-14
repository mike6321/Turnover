package com.example.joguk.criminalintent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }
    public void setId(UUID id) {
        mId = id;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    public String getSuspect() { return mSuspect; }
    public void setSuspect(String suspect) { mSuspect = suspect; }
    public String getPhotoFilename() { return "IMG_" + getId().toString() + ".jpg"; }

    public static String getDateString(Date date) {
        String dateFormat = "EEEyyyyMMMdd";
        String datePattern = android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(), dateFormat);
        SimpleDateFormat formatDate = new SimpleDateFormat(datePattern, Locale.getDefault());
        return formatDate.format(date);
    }

    public static String getTimeString(Date date) {
        String timeFormat = "hhmmss";
        String timePattern = android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(), timeFormat);
        SimpleDateFormat formatTime = new SimpleDateFormat(timePattern, Locale.getDefault());
        return formatTime.format(date);
    }
}
