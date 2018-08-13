package github.bbubbush.com.gamebasic;

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
    Bitmap spaceship;
    int spaceship_x, spaceship_y;
    int spaceshipWidth;
    Bitmap leftKey, rightKey;
    int leftKey_x, leftKey_y;
    int rightKey_x, rightKey_y;
    int width, height;
    int button_width;
    int score;

    Bitmap missileButton;
    int missileButton_x, missileButton_y;
    int missileWidth;
    int missile_middle;
    Bitmap missile;
    Bitmap planetimg;

    int count;
    ArrayList<MyMissile> myM;
    ArrayList<Planet> planet;
    Bitmap screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        // 사용하는 단말기의 스크린 가로, 세로 길이를 얻어오는 방법
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        myM = new ArrayList<MyMissile>();
        planet = new ArrayList<Planet>();

        // 우주선이미지를 비트맵으로 생성
        spaceship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);

        int x = width / 8;
        int y = height / 11;

        spaceship = Bitmap.createScaledBitmap(spaceship, x, y, true);
        spaceshipWidth = spaceship.getWidth();
        spaceship_x = width * 1 / 9;
        spaceship_y = height * 6 / 9;

        button_width = width / 6;

        leftKey = BitmapFactory.decodeResource(getResources(), R.drawable.leftkey);
        leftKey_x = width * 5 / 9;
        leftKey_y = height * 7 / 9;
        leftKey = Bitmap.createScaledBitmap(leftKey, button_width, button_width, true);

        rightKey = BitmapFactory.decodeResource(getResources(), R.drawable.rightkey);
        rightKey_x = width * 7 / 9;
        rightKey_y = height * 7 / 9;
        rightKey = Bitmap.createScaledBitmap(rightKey, button_width, button_width, true);

        missileButton = BitmapFactory.decodeResource(getResources(), R.drawable.missilebutton);
        missileButton = Bitmap.createScaledBitmap(missileButton, button_width, button_width, true);
        missileButton_x = width * 1 / 11;
        missileButton_y = height * 7 / 9;

        missile = BitmapFactory.decodeResource(getResources(), R.drawable.missile0);
        missile = Bitmap.createScaledBitmap(missile, button_width / 4, button_width / 4, true);
        missileWidth = missile.getWidth();

        planetimg = BitmapFactory.decodeResource(getResources(), R.drawable.planet);
        planetimg = Bitmap.createScaledBitmap(planetimg, button_width, button_width, true);

        screen = BitmapFactory.decodeResource(getResources(), R.drawable.screen);
        screen = Bitmap.createScaledBitmap(screen, width, height, true);

    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
            setBackgroundColor(Color.BLUE);
            gHandler.sendEmptyMessageDelayed(0, 1000);
        }

        @Override
        synchronized public void onDraw(Canvas canvas) {
            Random r1 = new Random();
            int x = r1.nextInt(width);

            if(planet.size() < 5){
                planet.add(new Planet(x, 100));
            }

            Paint p1 = new Paint();
            p1.setColor(Color.RED);
            p1.setTextSize(50);

            canvas.drawBitmap(screen, 0, 0, p1);
            canvas.drawText(Integer.toString(count), 0, 300, p1);
            canvas.drawText("점수 : " + Integer.toString(score), 0, 200, p1);
            canvas.drawBitmap(spaceship, spaceship_x, spaceship_y, p1);
            canvas.drawBitmap(leftKey, leftKey_x, leftKey_y, p1);
            canvas.drawBitmap(rightKey, rightKey_x, rightKey_y, p1);
            canvas.drawBitmap(missileButton, missileButton_x, missileButton_y, p1);
            for(MyMissile tmp : myM){
                canvas.drawBitmap(missile, tmp.x, tmp.y, p1);
            }
            for(Planet tmp : planet){
                canvas.drawBitmap(planetimg, tmp.x, tmp.y, p1);
            }

            moveMissile();
            movePlanet();
            checkCollision();
            count++;
        }

        public void moveMissile(){
            for (int i = myM.size() -1; i >= 0; i--){
                myM.get(i).move();
            }
            // 화면 벗어난 미사일 제거
            for (int i = myM.size() -1; i >= 0; i--){
                if(myM.get(i).y < 0){
                    myM.remove(i);
                }
            }
        }

        public void movePlanet(){
            for (int i = planet.size() -1; i >= 0; i--){
                planet.get(i).move();
            }
            // 화면 벗어난 행성 제거
            for (int i = planet.size() -1; i >= 0; i--){
                if(planet.get(i).x < 0 || planet.get(i).x > width){
                    planet.remove(i);
                }
            }
        }

        public void checkCollision(){
            for (int i = planet.size() - 1; i >= 0; i--){
                for (int j = myM.size() - 1; j >= 0; j--){
                    if(myM.get(j).x + missile_middle > planet.get(i).x
                            && myM.get(j).x + missile_middle < planet.get(i).x + button_width
                            && myM.get(j).y > planet.get(i).y
                            && myM.get(j).y < planet.get(i).y + button_width){
                        planet.remove(i);
                        myM.get(j).y = -30;
                        score += 10;
                    }
                }
            }
        }

        Handler gHandler = new Handler(){
            public void handleMessage(Message msg){
                invalidate();
                gHandler.sendEmptyMessageDelayed(0, 30);
            }
        };

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = 0, y = 0;
            if(event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_MOVE){
                x = (int) event.getX();
                y = (int) event.getY();
            }

            // 왼쪽 키 버튼 터치
            if((x > leftKey_x) && (x < leftKey_x + button_width)
                    && (y > leftKey_y) && (x < leftKey_y + button_width)){
                if(spaceship_x <= 0){
                    spaceship_x = 0;
                }else{
                    spaceship_x -= 20;
                }
            }

            // 오른쪽 키 버튼 터치
            if((x > rightKey_x) && (x < rightKey_x + button_width)
                    && (y > rightKey_y) && (x < rightKey_y + button_width)){
                if(spaceship_x >= width - spaceshipWidth){
                    spaceship_x = width - spaceshipWidth;
                }else{
                    spaceship_x += 20;
                }
            }

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                if((x > missileButton_x) && (x < missileButton_x + button_width)
                        && (y > missileButton_y)
                        && (x < missileButton_y + button_width)){
                    if(myM.size() < 10){
                        myM.add(new MyMissile(spaceship_x + spaceshipWidth / 2 - missileWidth / 2, spaceship_y));
                    }
                }
            }
            return true;        // 제대로 처리되었다는 의미
        }
    }

}


