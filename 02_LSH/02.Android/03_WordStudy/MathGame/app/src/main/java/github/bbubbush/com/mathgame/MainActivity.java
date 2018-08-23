package github.bbubbush.com.mathgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Bitmap mBasket;              // 바구니 이미지
    int mBasket_x, mBasket_y;   // 바구니의 좌표
    int mBasketWidth;           // 바구니의 가로
    int mBasketHeight;          // 바구니 세로

    Bitmap mLeftKey;            // 왼쪽버튼 이미지
    int mLeftKey_x, mLeftKey_y; // 왼쪽버튼 좌표

    Bitmap mRightKey;           // 오른쪽버튼 이미지
    int mRightKey_x, mRightKey_y;   // 오른쪽 버튼 좌표

    int mWidth;                     // 스크린 가로
    int mHeight;                    // 스크린 세로

    int mScore;                     // 점수

    int button_width;               // 버튼 가로

    Bitmap mBalloon;        // 풍선 이미지
    int mBalloonWidth;      // 풍선의 가로
    int mBalloonHeight;     // 풍선의 세로

    AnswerBalloon mAnswerBalloon;   // 정답풍선 객체

    int mCount;              // 횟수

    ArrayList<Balloon> mBalloons;       // 오답풍선 객체
    Bitmap mScreen;          // 스크린 이미지

    int number1, number2;       // 덧셈에 사용할 숫자
    int mAnswer;                 // 정답숫자
    int [] mWrongNumber = new int[5];         // 오답숫자

//    int ballon_speed;               // 풍선 속도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));       // this는 현재 activity

        // 변수 초기화
        mBalloons = new ArrayList<Balloon>();
//        mWrongNumber = new int[5];

        // 디바이크 화면 크기
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mWidth = display.getWidth();
        mHeight = display.getHeight();

        int x = mWidth / 4;
        int y = mHeight /14;

        button_width = mWidth / 6;  // 버튼 가로

        // 바구니
        mBasket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(mBasket, x, y, true);
        mBasketWidth = mBasket.getWidth();
        mBasketHeight = mBasket.getHeight();
        mBasket_x = mWidth * 1 / 9;
        mBasket_y = mHeight * 6 / 9;

        // 왼쪽 방향키
        mLeftKey = BitmapFactory.decodeResource(getResources(), R.drawable.leftkey);
        mLeftKey_x = mWidth * 5 / 9;
        mLeftKey_y = mHeight * 7 / 9;
        mLeftKey = Bitmap.createScaledBitmap(mLeftKey, button_width, button_width, true);

        // 오른쪽 방향키
        mRightKey = BitmapFactory.decodeResource(getResources(), R.drawable.rightkey);
        mRightKey_x = mWidth * 7 / 9;
        mRightKey_y = mHeight * 7 / 9;
        mRightKey = Bitmap.createScaledBitmap(mRightKey, button_width, button_width, true);

        // 풍선
        mBalloon = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);
        mBalloon = Bitmap.createScaledBitmap(mBalloon, button_width, button_width + button_width / 4, true);


        // 풍선 가로 세로 길이 세팅
        mBalloonWidth = mBalloon.getWidth();
        mBalloonHeight = mBalloon.getHeight();

        // 스크린
        mScreen = BitmapFactory.decodeResource(getResources(), R.drawable.screenmath);
        mScreen = Bitmap.createScaledBitmap(mScreen, mWidth, mHeight, true);

        // 정답풍선 생성
        Random r1 = new Random();
        int xx = r1.nextInt(mWidth);
        mAnswerBalloon = new AnswerBalloon(xx, 0, 5);
    }

    class MyView extends View{
        MyView(Context context){
            super(context); // 상위 클래스의 생성자를 호출해야 함
            setBackgroundColor(Color.BLUE);
            gHandler.sendEmptyMessageDelayed(0, 1000);
            makeQuestion();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 오답풍선 생성
            if (mBalloons.size() < 5){
                Random rd = new Random();
                int x = rd.nextInt(mWidth - button_width);
                int y = rd.nextInt(mHeight / 4);
                mBalloons.add(new Balloon(x, -y, 5));
            }

            Paint p1 = new Paint();
            p1.setColor(Color.WHITE);
            p1.setTextSize(mWidth / 14);
            canvas.drawBitmap(mScreen, 0, 0, p1);   // 게임 배경

            canvas.drawText("점수 : " + Integer.toString(mScore), 0 , mHeight * 1 / 12, p1);
            canvas.drawText("문제 : " + Integer.toString(number1) + "+" + Integer.toString(number2), 0, mHeight * 2 / 12, p1);
            canvas.drawBitmap(mBasket, mBasket_x, mBasket_y, p1);
            canvas.drawBitmap(mLeftKey, mLeftKey_x, mLeftKey_y, p1);
            canvas.drawBitmap(mRightKey, mRightKey_x, mRightKey_y, p1);

            // 오답 풍선 그리기
            for (Balloon temp: mBalloons){
                canvas.drawBitmap(mBalloon, temp.x, temp.y, p1);
            }
            for (int i = mBalloons.size() - 1; i >= 0; i--){
                canvas.drawText(Integer.toString(mWrongNumber[i]), mBalloons.get(i).x + mBalloonWidth / 6, mBalloons.get(i).y + mBalloonWidth * 2 / 3, p1);
            }

            // 정답 풍선 그리기
            canvas.drawBitmap(mBalloon, mAnswerBalloon.x, mAnswerBalloon.y, p1);
            canvas.drawText(Integer.toString(mAnswer), mAnswerBalloon.x + mBalloonWidth / 6, mAnswerBalloon.y + mBalloonHeight * 2 / 3, p1);

            // 정답 풍선 처리하기
            if (mAnswerBalloon.y > mHeight){
                mAnswerBalloon.y = -50;
            }

            // 풍선 움직이기
            moveBalloon();

            // 풍선과 바구니가 맞닿았는지 확인
            checkCollision();
            mCount++;
        }

        // 문제 생성
        public void makeQuestion(){
            Random rd = new Random();

            // 정답 풍선에 들어갈 숫자
            int x = rd.nextInt(99) + 1;
            number1 = x;
            x = rd.nextInt(99) + 1;
            number2 = x;
            mAnswer =  number1 + number2;

            // 오답 풍선에 들어갈 숫자
            for (int i = 0; i < 5; i++){
                x = rd.nextInt(197) + 1;
                // 오답이 정답과 같으면 다시 생성
                while(x == mAnswer){
                    x = rd.nextInt(197) + 1;
                }
                mWrongNumber[i] = x;
            }
        }

        // 풍선 이동
        public void moveBalloon(){
            // 오답풍선 이동
            for (int i = mBalloons.size() - 1; i >= 0; i--){
                mBalloons.get(i).move();
            }

            // 오답풍선이 맨 아래를 넘으면 다시 맨 위에서 시작
            for (int i = mBalloons.size() - 1; i >= 0; i--){
                if (mBalloons.get(i).y > mHeight) {
                    mBalloons.get(i).y = -100;
                }
            }
            // 정답풍선 이동
            mAnswerBalloon.move();
        }


        // 정답체크
        public void checkCollision(){
            // 바구니와 오답 풍선이 접촉했는지 체크
            for (int i = mBalloons.size() - 1; i >= 0; i--){
                if (mBalloons.get(i).x + mBalloonWidth / 2 > mBasket_x
                        && mBalloons.get(i).x + mBalloonWidth / 2 < mBasket_x + mBasketWidth
                        && mBalloons.get(i).y + mBalloonHeight > mBasket_y
                        && mBalloons.get(i).y + mBalloonHeight > mBasket_x + mBasketWidth){     // 이상한 부분
                    mBalloons.remove(i);
                    mScore -= 10;
                }
            }

            // 바구니와 정답 풍선이 접촉했는지 체크
            if (mAnswerBalloon.x + mBalloonWidth / 2 > mBasket_x
                    && mAnswerBalloon.x + mBalloonWidth / 2 < mBasket_x + mBasketWidth
                    && mAnswerBalloon.y + mBalloonHeight > mBasket_y
                    && mAnswerBalloon.y + mBalloonHeight > mBasket_x +mBasketWidth){            // 이상훈 부분
                mScore += 30;
                makeQuestion();
                Random rd = new Random();
                int xx = rd.nextInt(mWidth - button_width);
                mAnswerBalloon.x = xx;
                xx = rd.nextInt(300);
                mAnswerBalloon.y = -xx;
            }
        }

        Handler gHandler = new Handler(){
            public void handleMessage(Message msg){
                invalidate();
                gHandler.sendEmptyMessageDelayed(0, 30);

            }
        };

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            int x = 0;
            int y = 0;

            if (e.getAction() == MotionEvent.ACTION_DOWN
                    || e.getAction() == MotionEvent.ACTION_MOVE){
                x = (int) e.getX();
                y = (int) e.getY();     // invalidate();
            }

            // 왼쪽 이동
            if ((x > mLeftKey_x) && (x < mLeftKey_x + button_width)
                    && (y > mLeftKey_y) && (x < mLeftKey_y + button_width)){
                if (mBasket_x <= 0){
                    mBasket_x = 0;
                } else {
                    mBasket_x -= 20;
                }
            }

            // 오른쪽 이동
            if ((x > mRightKey_x) && (x < mRightKey_x + button_width)
                    && (y > mRightKey_y) && (x < mRightKey_y + button_width)){
                if (mBasket_x >= mWidth - mBasketWidth){
                    mBasket_x = mWidth - mBasketWidth;
                } else {
                    mBasket_x += 20;
                }
            }
            return true;
        }
    }


}

