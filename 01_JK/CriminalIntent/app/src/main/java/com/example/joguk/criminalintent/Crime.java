package com.example.joguk.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;            // Unique ID
    private String mTitle;       // Title
    private Date mDate;          // crime reg date
    private boolean mSolved;    // isSolved?

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public boolean isSolved() { return mSolved; }
    public void setSolved(boolean solved) { mSolved = solved; }
    public Date getDate() { return mDate; }
    public void setDate(Date date) { mDate = date; }
    public String getTitle() { return mTitle; }
    public void setTitle(String title) { mTitle = title; }
    public UUID getId() { return mId; }
    public void setId(UUID id) { mId = id; }}
