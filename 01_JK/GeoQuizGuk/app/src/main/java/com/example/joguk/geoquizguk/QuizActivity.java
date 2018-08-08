package com.example.joguk.geoquizguk;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joguk.geoquizguk.library.CircleIndicator;
import com.example.joguk.geoquizguk.library.LineIndicator;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    // member Variable
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mCheatButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private TextView mLengthTextView;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    // indicator variable
    private SamplePagerAdapter spa;
    private ViewPager viewPager;
    private LineIndicator lineIndicator;

    // Question Obj Array
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // savedInstanceState가 있으면
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // init
        this.init();

        this.updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            // static method call
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void init(){
        this.mLengthTextView = (TextView) findViewById(R.id.length_text_view);
        this.mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        this.mTrueButton = (Button) findViewById(R.id.true_button);
        this.mFalseButton = (Button) findViewById(R.id.false_button);
        this.mCheatButton = (Button) findViewById(R.id.cheat_button);
        this.mPrevButton = (Button) findViewById(R.id.prev_button);
        this.mNextButton = (Button) findViewById(R.id.next_button);

        // indicator Init
        this.spa = new SamplePagerAdapter(getSupportFragmentManager());
        this.spa.setItemCount(this.mQuestionBank.length);

        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPager.setAdapter(this.spa);

        this.lineIndicator = (LineIndicator) findViewById(R.id.line_indicator);
        this.lineIndicator.setupWithViewPager(this.viewPager);

/*      CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        circleIndicator.setupWithViewPager(viewPager);*/

        this.mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        this.mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        this.mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        this.mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processMoveIndex(1);
            }
        });

        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processMoveIndex(-1);
            }
        });
    }

    private void processMoveIndex(int delta) {
        mCurrentIndex = (mCurrentIndex - delta + mQuestionBank.length) % mQuestionBank.length;
        mIsCheater = false;
        updateQuestion();

        viewPager.setCurrentItem(mCurrentIndex);
    }

    private void updateQuestion() {
        int question = this.mQuestionBank[mCurrentIndex].getTextResId();
        this.mQuestionTextView.setText(question);

        // Length Text change
//        this.mLengthTextView.setText((this.mCurrentIndex + 1) + " / " + this.mQuestionBank.length);
    }

    private void checkAnswer(boolean userPressedTrue) {
        // Member Variable
        boolean answerIsTrue = this.mQuestionBank[this.mCurrentIndex].isAnswerTrue();   // 해당 index의 answer의 bool variable 가져오기
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    // app Life Cycle method
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
//        outState
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }
}