
// test
package github.bbubbush.com.a03_englishvoca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class StudyView2 extends SurfaceView implements Callback {

    static int questionNumber = 0;
    static int numberOfquestion = 9;

    static int soundOk = 1;

    int solvingOver = 0;
    int btnResultConfirmOk = 0;

    int[] ox = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    int answerButton = 0;
    int answerUser = 0;

    int starIng = 0;
    int starIndex = 0;
    int starX, starY;

    int oNumber = 0;
    int xNumber = 0;

    int submenuOk = 1;
    int submenuOk2 = 0;

    int textSize = 49;

    String[] wordForDelete = {"", "", "", "", ""};

    static StudyThread mThread;
    static SurfaceHolder mHolder;
    static Context mContext;

    FileTable mFile2;

    DBHelper m_helper;

    Cursor cursor;
    int dicOk = 0;
    int movePosition = 0;

    MyButton2 btnSetting;
    MyButton2 btnExit;

    MyButton2 btnNum1;   //btn : number1
    MyButton2 btnNum2;   //btn : number2
    MyButton2 btnNum3;  //btn : number3
    MyButton2 btnNum4;  //btn : number4
    MyButton2 btnNum5;  //btn : number5
    MyButton2 btnNextQuestion;

    //sub menu
    MyButton2 btn10Questioon;            //문제10 버튼
    MyButton2 btn20Questioon;
    MyButton2 btn25Questioon;
    MyButton2 btn50Questioon;
    MyButton2 btn100Questioon;

    MyButton2 btnMenuClose;

    MyButton2 btnResultConfirm;
    MyButton2 btnExplain;  //explain button
    int explainPressed = 0;

    // sub menu
    MyButton2 btnOx;   //평가 후 문항별 정오답 확인하기

    MyButton2 btnLeftArrow;   //left
    MyButton2 btnRightArrow;   //right
    MyButton2 btnClose2;  //결과보기에서 제시되는 닫기 버튼

    MyButton2 btnDeleteDb;
    MyButton2 exitButton;

    int btnPressed=0;

    int btnNum1Count = 0;
    int btnNum2Count = 0;
    int btnNum3Count = 0;
    int btnNum4Count = 0;

    static int Width, Height;
    int level;
    int score = 0;

    Bitmap answerx;
    Bitmap answero;

    Bitmap explain;
    Bitmap star[] = new Bitmap[4];

    Bitmap testtitle;

    Bitmap screenPink;

    static SoundPool sdPool;
    static int dingdongdaeng, taeng;

    public StudyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;
        mContext = context;
        mThread = new StudyThread(holder, context);

        initAll();
        makeQuestion(level);
        setFocusable(true);
    }

    private void initAll() {
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();

        m_helper = new DBHelper(mContext, "test.db", null, 1);

        mFile2 = new FileTable();

        //본 예제에서는 4지 선다로 제작함
        btnNum1 = new MyButton2(Width / 14, Height / 3, 7); // number 1
        btnNum2 = new MyButton2(btnNum1.x, btnNum1.y + btnNum1.h * 2 + 8, 8); // number 2
        btnNum3 = new MyButton2(btnNum1.x, btnNum2.y + btnNum2.h * 2 + 8, 9); // number 3
        btnNum4 = new MyButton2(btnNum1.x, btnNum3.y + btnNum3.h * 2 + 8, 10); // number 4
        btnNum5 = new MyButton2(btnNum1.x, btnNum4.y + btnNum4.h * 2 + 8, 11); // number 5 사용안함

        btnSetting = new MyButton2(Width - btnNum1.w * 8, btnNum1.h, 14, 0);
        btnExit = new MyButton2(Width - btnSetting.w * 3, btnSetting.y, 5);  //exit
        testtitle = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.testtitle);
        testtitle = Bitmap.createScaledBitmap(testtitle, btnSetting.w * 6, btnSetting.h * 2, true);

        btnNextQuestion = new MyButton2(Width * 2 / 3, Height / 2, 12);  //다음문제 버튼

        // 문제수 선택 버튼
        btn10Questioon = new MyButton2(Width / 10, Height / 2, 5, 0);
        btn20Questioon = new MyButton2(btn10Questioon.x + btn10Questioon.w * 2, btn10Questioon.y, 6, 0);
        btn25Questioon = new MyButton2(btn10Questioon.x + btn10Questioon.w * 4, btn10Questioon.y, 7, 0);
        btn50Questioon = new MyButton2(btn10Questioon.x + btn10Questioon.w * 6, btn10Questioon.y, 8, 0);
        btn100Questioon = new MyButton2(btn10Questioon.x + btn10Questioon.w * 8, btn10Questioon.y, 9, 0);

        // 메뉴 닫기 버튼
        btnMenuClose = new MyButton2(Width / 2 + btnSetting.w * 3, Height - btnSetting.h * 3, 24);

        //확인하기 버튼
        btnResultConfirm = new MyButton2(Width / 2 - btnSetting.w, Height - btnSetting.h * 2, 25);

        //해설 버튼
        btnExplain = new MyButton2(Width / 40, Height * 7 / 10, 31);

        //결과보기 버튼
        btnOx = new MyButton2(Width / 4, btnMenuClose.y, 32);  // Button for check the questions u solved 결과보기 버튼

        // left, right arrow in circle
        btnLeftArrow = new MyButton2(btnNum1.w, Height - btnNum1.h * 3, 26);
        btnRightArrow = new MyButton2(btnLeftArrow.x + btnLeftArrow.w * 3, Height - btnNum1.h * 3, 27);

        // 결과보기에서 사용되는 닫기 버튼
        btnClose2 = new MyButton2(Width - btnNum1.w * 3, Height - btnNum1.h * 3, 28);

        //전체삭제 버튼
        btnDeleteDb = new MyButton2(Width * 9 / 10, 3, 30);

        exitButton = new MyButton2(10, 600, 4);

        int xxx = Width / 6;
        answerx = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answerx);
        answerx = Bitmap.createScaledBitmap(answerx, xxx, xxx, true);
        answero = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answero);
        answero = Bitmap.createScaledBitmap(answero, xxx, xxx, true);
        explain = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explain);

        screenPink = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.subscreen00);
        screenPink = Bitmap.createScaledBitmap(screenPink, Width - 2, Height - Height / 5 - btnNum1.h / 4, true);

        for (int i = 0; i < 4; i++) {
            star[i] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circlewhite);
            star[i] = Bitmap.createScaledBitmap(star[i], btnNum1.w * 2 + i * 2, btnNum1.w * 2 + i * 2, true);
        }

        sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        dingdongdaeng = sdPool.load(mContext, R.raw.dingdongdaeng, 1);
        taeng = sdPool.load(mContext, R.raw.taeng, 2);
        btn10Questioon.pressed = 1;
    }

    public void makeQuestion(int x) {

        mFile2.loadFile2(x);
        SQLiteDatabase db = m_helper.getWritableDatabase();
        db.delete("test", null, null);
        db.close();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mThread.start();

        } catch (Exception e) {
            RestartGame();

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void RestartGame() {
        mThread.StopThread();
        mThread = null;
        mThread = new StudyThread(mHolder, mContext);
        mThread.start();
    }

    class StudyThread extends Thread {
        boolean canRun = true;
        boolean isWait = false;
        int loop;
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        Paint paint4 = new Paint();
        Paint paint5 = new Paint();


        public StudyThread(SurfaceHolder holder, Context context) {

            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            paint.setTypeface(Typeface.create("", Typeface.BOLD));

            paint2.setColor(Color.BLUE);
            paint2.setAntiAlias(true);
            paint2.setTypeface(Typeface.create("", Typeface.BOLD));
            paint3.setAlpha(100);

            paint4.setColor(Color.WHITE);
            paint5.setColor(Color.RED);

        }

        public void DrawAll(Canvas canvas) {

            Paint pp = new Paint();
            pp.setColor(0xff22741c);
            Paint frame = new Paint();
            frame.setColor(0xff664b00);
            canvas.drawRect(0,0,Width,Height,frame);
            canvas.drawRect(btnNum1.w/2,btnNum1.w/2,Width-btnNum1.w/2,Height-btnNum1.w/2,pp);

            canvas.drawBitmap(testtitle, Width / 2 - btnNum1.w * 3, btnSetting.y, null);
            paint.setTextSize(Width/23);   // 객관식에 나오는 영어 글씨
            paint2.setTextSize(Width / 27); //파란색 중간크기 글씨
            paint4.setTextSize(Width/30);  // 해설글씨 크기
            paint5.setTextSize(Width/22);  //평가후 결과보기 O,X 빨간색


            canvas.drawText("문제수 : " + Integer.toString(numberOfquestion + 1), Width / 20, Height / 6, paint2); //numberofQuestion

            canvas.drawText(FileSplit1.questionNum[questionNumber][0] + ". " + FileSplit1.questionNum[questionNumber][1], btnNum1.x, Height * 7 / 24, paint);

            canvas.drawText(FileSplit1.questionNum[questionNumber][2], btnNum1.x + btnNum1.w * 3 - btnNum1.w / 3, btnNum1.y + btnNum1.w*3/2, paint);
            canvas.drawText(FileSplit1.questionNum[questionNumber][3], btnNum1.x + btnNum1.w * 3 - btnNum1.w / 3, btnNum2.y + btnNum1.w*3/2, paint);
            canvas.drawText(FileSplit1.questionNum[questionNumber][4], btnNum1.x + btnNum1.w * 3 - btnNum1.w / 3, btnNum3.y + btnNum1.w*3/2, paint);
            canvas.drawText(FileSplit1.questionNum[questionNumber][5], btnNum1.x + btnNum1.w * 3 - btnNum1.w / 3, btnNum4.y + btnNum1.w*3/2, paint);


            canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
            canvas.drawBitmap(btnNum2.button_img, btnNum2.x, btnNum2.y, null);
            canvas.drawBitmap(btnNum3.button_img, btnNum3.x, btnNum3.y, null);
           canvas.drawBitmap(btnNum4.button_img, btnNum4.x, btnNum4.y, null);

            canvas.drawBitmap(btnExit.button_img, btnExit.x, btnExit.y, null);
            canvas.drawBitmap(btnSetting.button_img, btnSetting.x, btnSetting.y, null);

            if (submenuOk2 == 1) {
                canvas.drawBitmap(btnOx.button_img, btnOx.x, btnOx.y, null);
            }

            if (answerButton == 1) {
                int sss = 0;
                sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());
                if (sss == answerUser) {

                    canvas.drawBitmap(answero, Width/2, btnNum1.y, null);

                } else canvas.drawBitmap(answerx, Width/2, btnNum1.y, null);


                if (questionNumber >= numberOfquestion) {
                    canvas.drawBitmap(btnResultConfirm.button_img, btnResultConfirm.x, btnResultConfirm.y, null); //문제를 다 푼 후 결과확인 , result confirm button
                    btnResultConfirmOk = 1;
                } else
                    canvas.drawBitmap(btnNextQuestion.button_img, btnNextQuestion.x, btnNextQuestion.y, null);


                canvas.drawBitmap(btnExplain.button_img, btnExplain.x, btnExplain.y, null);
                if (explainPressed == 1) {
                    canvas.drawText(FileSplit1.questionNum[questionNumber][7], btnNum1.w, btnNum4.y + btnNum4.h * 3 , paint4);
                    canvas.drawText(FileSplit1.questionNum[questionNumber][8], btnNum1.w, btnNum4.y + btnNum4.h * 4 +btnNum4.h/2, paint4);
                }

            }


            if (starIng == 1) {

                starIndex += 1;
                if (starIndex >= 15) {
                    starIng = 0;
                    starIndex = 0;
                } else
                    canvas.drawBitmap(star[starIndex / 4], starX - starIndex / 4, starY - starIndex / 4, null);
            }

            // 1~5

            if(btnPressed==1) {
                btnNum1Count++;
                btnNum2Count++;
                btnNum3Count++;
                btnNum4Count++;

            }

            if (btnNum1Count == 15) {
                btnNum1Count = 0;
                btnPressed=0;
                btnNum1.btn_released();
            }
            if (btnNum2Count == 15) {
                btnNum2Count = 0;
                btnPressed=0;
                btnNum2.btn_released();
            }
            if (btnNum3Count == 15) {
                btnNum3Count = 0;
                btnPressed=0;
                btnNum3.btn_released();
            }
            if (btnNum4Count == 15) {
                btnNum4Count = 0;
                btnPressed=0;
                btnNum4.btn_released();
            }


            if (submenuOk == 1) {
                canvas.drawBitmap(screenPink, 0, Height / 5, null);
                canvas.drawText("문제수를 선택하세요. ", 45, btnSetting.y + btnSetting.h * 2 + 80, paint2);

                if (btn10Questioon.pressed == 1)
                    canvas.drawBitmap(btn10Questioon.button_img, btn10Questioon.x, btn10Questioon.y, null);
                else
                    canvas.drawBitmap(btn10Questioon.button_img, btn10Questioon.x, btn10Questioon.y, paint3);

                if (btn20Questioon.pressed == 1)
                    canvas.drawBitmap(btn20Questioon.button_img, btn20Questioon.x, btn20Questioon.y, null);
                else
                    canvas.drawBitmap(btn20Questioon.button_img, btn20Questioon.x, btn20Questioon.y, paint3);

                if (btn25Questioon.pressed == 1)
                    canvas.drawBitmap(btn25Questioon.button_img, btn25Questioon.x, btn25Questioon.y, null);
                else
                    canvas.drawBitmap(btn25Questioon.button_img, btn25Questioon.x, btn25Questioon.y, paint3);

                if (btn50Questioon.pressed == 1)
                    canvas.drawBitmap(btn50Questioon.button_img, btn50Questioon.x, btn50Questioon.y, null);
                else
                    canvas.drawBitmap(btn50Questioon.button_img, btn50Questioon.x, btn50Questioon.y, paint3);

                if (btn100Questioon.pressed == 1)
                    canvas.drawBitmap(btn100Questioon.button_img, btn100Questioon.x, btn100Questioon.y, null);
                else
                    canvas.drawBitmap(btn100Questioon.button_img, btn100Questioon.x, btn100Questioon.y, paint3);

                canvas.drawBitmap(btnMenuClose.button_img, btnMenuClose.x, btnMenuClose.y, null); //�ݱ� ��ư
            }

            if (solvingOver == 1) {
                submenuOk = 0;
                canvas.drawBitmap(screenPink, 10, btnSetting.y + btnSetting.h * 2, null);

                switch (numberOfquestion) {
                    case 9:
                        score = oNumber * 10;
                        break;
                    case 19:
                        score = oNumber * 5;
                        break;
                    case 24:
                        score = oNumber * 4;
                        break;
                    case 49:
                        score = oNumber * 2;
                        break;
                    case 99:
                        score = oNumber * 1;
                        break;
                    default:
                        score = oNumber * 10;
                        break;

                }

                canvas.drawText("SCORE = " + Integer.toString(score) + "  점", Width / 15, btnSetting.w * 4, paint2);
                if (score >= 90)
                    canvas.drawText("영어단어 박사입니다. 아주 훌륭한 실력입니다. ^^", Width / 15, btnSetting.w * 7, paint2);
                else if (score >= 80)
                    canvas.drawText("우수한 점수입니다. 조금만 더 하면 당신은 영어박사", Width / 15, btnSetting.w * 7, paint2);
                else if (score >= 70)
                    canvas.drawText("아쉽지만 좀 더 노력하세요", Width / 15, btnSetting.w * 7, paint2);
                else
                    canvas.drawText("실망하지 마세요. 이제 다시 시작해보는 거에요^^", Width / 15, btnSetting.w * 7, paint2);


                canvas.drawBitmap(btnOx.button_img, btnOx.x, btnOx.y, null); //check my right, wrong solving
                canvas.drawBitmap(btnMenuClose.button_img, btnMenuClose.x, btnMenuClose.y, null);

            }

//평가 결과 5개씩 확인하기
            if (dicOk == 1) {
                canvas.drawRect(0,0,Width,Height,frame);
                canvas.drawRect(btnNum1.w/2,btnNum1.w/2,Width-btnNum1.w/2,Height-btnNum1.w/2,pp);
                SQLiteDatabase db = m_helper.getReadableDatabase();

                cursor = db.query("test", null, null, null, null, null, null);

                int numofdb = cursor.getCount();

                if (movePosition > numofdb) movePosition -= 5;
                else if (movePosition == numofdb) movePosition -= 5;

                if (movePosition <= 0) movePosition = 0;

                for (int i = 0; i < 5; i++) {

                    if (cursor.moveToPosition(movePosition + i) == false) break;

                    if (ox[movePosition + i] == 1)
                        canvas.drawText("O", btnNum1.w, 180 + 90 * i, paint5);
                    else canvas.drawText("X", btnNum1.w, 180 + 90 * i, paint5);


                    canvas.drawText((movePosition + i + 1) + " " + cursor.getString(1) + " : " + cursor.getString(2),
                            btnNum1.w * 2, 180 + 90 * i, paint);

                    wordForDelete[i] = cursor.getString(1);

                }
                //db.close();

                //reft, right arrow  and close button in circle format
                canvas.drawBitmap(btnLeftArrow.button_img, btnLeftArrow.x, btnLeftArrow.y, null);
                canvas.drawBitmap(btnRightArrow.button_img, btnRightArrow.x, btnRightArrow.y, null);
                canvas.drawBitmap(btnClose2.button_img, btnClose2.x, btnClose2.y, null);

                int x = 0;
                for (int i = 0; i < 5; i++) {
                    int imsy = 0;
                    if (numofdb == 0) {
                        canvas.drawText("", 70, 180 + 90 * i, paint);
                        break;
                    }

                    x = (numofdb - 1) / 5;
                    if ((movePosition) / 5 < x) imsy = 1;
                    else imsy = 0;

                    if (imsy == 0) {
                        if (numofdb % 5 == 4 && i == 3) break;
                        if (numofdb % 5 == 3 && i == 2) break;
                        if (numofdb % 5 == 2 && i == 1) break;
                        if (numofdb % 5 == 1 && i == 0) break;
                    }

                }


                // all delete button
                canvas.drawBitmap(btnDeleteDb.button_img, btnDeleteDb.x, btnDeleteDb.y, null);

                cursor.close();
                db.close();
            }

        }               // end of drawall

        public void run() {
            Canvas canvas = null;
            while (canRun) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {


                        DrawAll(canvas);

                    } // sync
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } // try


                synchronized (this) {
                    if (isWait)
                        try {
                            wait();
                        } catch (Exception e) {
                            // nothing
                        }
                } // sync

            } // while
        } // run


        public void StopThread() {
            canRun = false;
            synchronized (this) {
                this.notify();
            }
        }


        public void PauseNResume(boolean wait) {
            isWait = wait;
            synchronized (this) {
                this.notify();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;

        synchronized (mHolder) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                x = (int) event.getX();
                y = (int) event.getY();


            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

            }
        }   // end of sync

        //if(submenuOk==0)
        if (x > btnSetting.x && x < (btnSetting.x + btnSetting.w * 2) && y > btnSetting.y && y < (btnSetting.y + btnSetting.h * 2)) {

            explainPressed = 0;
            submenuOk = 1;

        }


        //exit button
        if (dicOk != 1)
            if (x > btnExit.x && x < (btnExit.x + btnExit.w * 2) && y > btnExit.y && y < (btnExit.y + btnExit.h * 2)) {
                SQLiteDatabase db = m_helper.getWritableDatabase();
                db.delete("test", null, null);
                db.close();
                btnExit.btn_press();
                System.exit(0);
                submenuOk = 0;
                dicOk = 0;
            }

        if (answerButton == 0) {
            // 1~4
            if (submenuOk == 0)
                if (x > btnNum1.x && x < (btnNum1.x + btnNum1.w * 2) && y > btnNum1.y && y < (btnNum1.y + btnNum1.h * 2)) {

                    int sss = 0;

                    submenuOk = 0;
                    answerUser = 1;
                    btnNum1.btn_press();
                    answerButton = 1;
                    btnNum1Count = 0;
                    btnPressed=1;
                    starIng = 1;
                    starX = btnNum1.x;
                    starY = btnNum1.y;

                    sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;

                        ox[questionNumber] = 1;
                    } else {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.taeng, 1, 1, 9, 0, 1);

                        ox[questionNumber] = 0;
                        xNumber++;
                    }
                }


            if (dicOk == 0 && submenuOk == 0)
                if (x > btnNum2.x && x < (btnNum2.x + btnNum2.w * 2) && y > btnNum2.y && y < (btnNum2.y + btnNum2.h * 2)) {

                    int sss = 0;

                    submenuOk = 0;
                    dicOk = 0;
                    btnNum2.btn_press();
                    answerButton = 1;
                    answerUser = 2;
                    btnNum2Count = 0;
                    btnPressed=1;
                    starIng = 1;
                    starX = btnNum2.x;
                    starY = btnNum2.y;

                    sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                        ox[questionNumber] = 1;
                    } else {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                        ox[questionNumber] = 0;
                    }

                }


            if (dicOk == 0 && submenuOk == 0)
                if (x > btnNum3.x && x < (btnNum3.x + btnNum3.w * 2) && y > btnNum3.y && y < (btnNum3.y + btnNum3.h * 2)) {
                    btnNum3.btn_press();
                    answerButton = 1;
                    answerUser = 3;
                    submenuOk = 0;
                    dicOk = 0;
                    btnNum3Count = 0;
                    btnPressed=1;
                    starIng = 1;
                    starX = btnNum3.x;
                    starY = btnNum3.y;
                    int sss = 0;
                    sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());
                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                        ox[questionNumber] = 1;
                    } else {
                        xNumber++;
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.taeng, 1, 1, 9, 0, 1);
                        ox[questionNumber] = 0;
                    }

                }


            if (dicOk == 0 && submenuOk == 0)
                if (x > btnNum4.x && x < (btnNum4.x + btnNum4.w * 2) && y > btnNum4.y && y < (btnNum4.y + btnNum4.h * 2)) {
                    int sss = 0;
                    btnNum4.btn_press();
                    answerButton = 1;
                    answerUser = 4;
                    btnNum4Count = 0;
                    btnPressed=1;
                    submenuOk = 0;
                    dicOk = 0;
                    starIng = 1;
                    starX = btnNum4.x;
                    starY = btnNum4.y;
                    sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                        ox[questionNumber] = 1;
                    } else {
                        xNumber++;
                        if (soundOk == 1)
                            StudyView2.sdPool.play(StudyView2.taeng, 1, 1, 9, 0, 1);
                        ox[questionNumber] = 0;
                    }

                }

        } // end of answerButton


        //본예제에서는 사용안함
        if (dicOk == 0 && submenuOk == 0)
            if (x > btnNum5.x && x < (btnNum5.x + btnNum5.w * 2) && y > btnNum5.y && y < (btnNum5.y + btnNum5.h * 2)) {
                btnNum5.btn_press();
                submenuOk = 0;
                dicOk = 0;
            }

        if (answerButton == 1 && btnResultConfirmOk != 1)
            if (x > btnNextQuestion.x && x < (btnNextQuestion.x + btnNextQuestion.w * 2) && y > btnNextQuestion.y && y < (btnNextQuestion.y + btnNextQuestion.h * 2)) {
                btnNextQuestion.btn_press();
                answerButton = 0;
                explainPressed = 0;
                submenuOk = 0;
                int sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());
                dicOk = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();
                String sql = String.format("INSERT INTO test VALUES(Null, '%s', '%s');",
                        FileSplit1.questionNum[questionNumber][sss + 1], FileSplit1.questionNum[questionNumber][1]);

                db.execSQL(sql);
                db.close();
                questionNumber += 1;
            }

        // submenu 1
        if (submenuOk == 1)
            if (x > btn10Questioon.x && x < (btn10Questioon.x + btn10Questioon.w * 2) && y > btn10Questioon.y && y < (btn10Questioon.y + btn10Questioon.h * 2)) {
                btn10Questioon.pressed = 1;
                btn20Questioon.pressed = 0;
                btn25Questioon.pressed = 0;
                btn50Questioon.pressed = 0;
                btn100Questioon.pressed = 0;
                numberOfquestion = 9;
            }

        if (submenuOk == 1)
            if (x > btn20Questioon.x && x < (btn20Questioon.x + btn20Questioon.w * 2) && y > btn20Questioon.y && y < (btn20Questioon.y + btn20Questioon.h * 2)) {
                btn10Questioon.pressed = 0;
                btn20Questioon.pressed = 1;
                btn25Questioon.pressed = 0;
                btn50Questioon.pressed = 0;
                btn100Questioon.pressed = 0;
                numberOfquestion = 19;
            }

        if (submenuOk == 1)
            if (x > btn25Questioon.x && x < (btn25Questioon.x + btn25Questioon.w * 2) && y > btn25Questioon.y && y < (btn25Questioon.y + btn25Questioon.h * 2)) {
                btn10Questioon.pressed = 0;
                btn20Questioon.pressed = 0;
                btn25Questioon.pressed = 1;
                btn50Questioon.pressed = 0;
                btn100Questioon.pressed = 0;
                numberOfquestion = 24;
            }

        if (submenuOk == 1)
            if (x > btn50Questioon.x && x < (btn50Questioon.x + btn50Questioon.w * 2) && y > btn50Questioon.y && y < (btn50Questioon.y + btn50Questioon.h * 2)) {
                btn10Questioon.pressed = 0;
                btn20Questioon.pressed = 0;
                btn25Questioon.pressed = 0;
                btn50Questioon.pressed = 1;
                btn100Questioon.pressed = 0;
                numberOfquestion = 49;
            }

        if (submenuOk == 1)
            if (x > btn100Questioon.x && x < (btn100Questioon.x + btn100Questioon.w * 2) && y > btn100Questioon.y && y < (btn100Questioon.y + btn100Questioon.h * 2)) {
                btn10Questioon.pressed = 0;
                btn20Questioon.pressed = 0;
                btn25Questioon.pressed = 0;
                btn50Questioon.pressed = 0;
                btn100Questioon.pressed = 1;
                numberOfquestion = 99;
            }

        // close button  .. init question   btnMenuClose is close button
        if (submenuOk == 1)
            if (x > btnMenuClose.x && x < (btnMenuClose.x + btnMenuClose.w * 2) && y > btnMenuClose.y && y < (btnMenuClose.y + btnMenuClose.h * 2)) {
                questionNumber = 0;
                submenuOk = 0;
                dicOk = 0;
                oNumber = 0;
                xNumber = 0;
                answerButton = 0;
                solvingOver = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();
                db.delete("test", null, null);  //��ü ����
                db.close();
                makeQuestion(level);
            }
        // 문제를 다 푼 후 결과확인 닫기 버튼
        if (solvingOver == 1)
            if (x > btnMenuClose.x && x < (btnMenuClose.x + btnMenuClose.w * 2) && y > btnMenuClose.y && y < (btnMenuClose.y + btnMenuClose.h * 2)) {
                solvingOver = 0;
                submenuOk = 1;
                questionNumber = 0;
                dicOk = 0;
                btnResultConfirmOk = 0;
            }

        if (btnResultConfirmOk == 1)
            if (x > btnResultConfirm.x && x < (btnResultConfirm.x + btnResultConfirm.w * 2) && y > btnResultConfirm.y && y < (btnResultConfirm.y + btnResultConfirm.h * 2)) {
                solvingOver = 1;
                submenuOk = 0;
                btnResultConfirmOk = 0;

                int sss = Integer.parseInt(FileSplit1.questionNum[questionNumber][6].trim());

                dicOk = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();
                String sql = String.format("INSERT INTO test VALUES(NULL, '%s', '%s');",
                        FileSplit1.questionNum[questionNumber][sss + 1],
                        FileSplit1.questionNum[questionNumber][1]);
                db.execSQL(sql);
                db.close();
            }


        if (answerButton == 1 && dicOk == 0)
            if (x > btnExplain.x && x < (btnExplain.x + btnExplain.w * 2) && y > btnExplain.y && y < (btnExplain.y + btnExplain.h * 2)) {
                explainPressed = 1;
                submenuOk = 0;
            }


        if (solvingOver == 1)
            if (x > btnOx.x && x < (btnOx.x + btnOx.w * 2) && y > btnOx.y && y < (btnOx.y + btnOx.h * 2)) {
                dicOk = 1;
                submenuOk = 0;
            }

        // left arrow button in circle
        if (dicOk == 1)
            if (x > btnLeftArrow.x && x < (btnLeftArrow.x + btnLeftArrow.w * 2) && y > btnLeftArrow.y && y < (btnLeftArrow.y + btnLeftArrow.h * 2)) {
                btnLeftArrow.btn_press();
                submenuOk = 0;
                movePosition -= 5;
                if (movePosition < 0) movePosition = 0;
            }

        if (dicOk == 1)
            if (x > btnRightArrow.x && x < (btnRightArrow.x + btnRightArrow.w * 2) && y > btnRightArrow.y && y < (btnRightArrow.y + btnRightArrow.h * 2)) {
                btnRightArrow.btn_press();
                submenuOk = 0;
                movePosition += 5;
            }

        if (dicOk == 1)
            if (x > btnClose2.x && x < (btnClose2.x + btnClose2.w * 2) && y > btnClose2.y && y < (btnClose2.y + btnClose2.h * 2)) {
                btnClose2.btn_press();
                submenuOk = 1;
                dicOk = 0;
            }

        if (dicOk == 1)
            if (x > btnDeleteDb.x && x < (btnDeleteDb.x + btnDeleteDb.w * 2) && y > btnDeleteDb.y && y < (btnDeleteDb.y + btnDeleteDb.h * 2)) {
                SQLiteDatabase db = m_helper.getWritableDatabase();
                db.delete("test", null, null);
                db.close();
            }

        return true;
    }  //End of onTouchEvent


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) return true;
        synchronized (mHolder) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:

                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:

                    break;
                case KeyEvent.KEYCODE_DPAD_UP:

                    break;
                default:

            }
        }
        return false;
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE test (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT, age INTEGER);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS test");
            onCreate(db);

        }
    }

} // SurfaceView
