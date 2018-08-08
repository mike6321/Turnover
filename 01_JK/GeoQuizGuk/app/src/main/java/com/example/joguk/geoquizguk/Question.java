package com.example.joguk.geoquizguk;

public class Question {
    // Member Variable
    private int mTextResId;
    private boolean mAnswerTrue;

    // constructor
    public Question(int textResId, boolean answerTrue) {
        this.mTextResId = textResId;
        this.mAnswerTrue = answerTrue;
    }

    // Getter & Setter
    public int getTextResId() {return this.mTextResId;}
    public void setTextResId(int textResId) { this.mTextResId = textResId; }
    public boolean isAnswerTrue() { return this.mAnswerTrue; }
    public void setAnswerTrue(boolean answerTrue) { this.mAnswerTrue = answerTrue; }
}