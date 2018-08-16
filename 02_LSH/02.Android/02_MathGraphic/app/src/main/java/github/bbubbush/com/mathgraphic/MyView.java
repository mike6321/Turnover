package github.bbubbush.com.mathgraphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    Paint p1 = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    Paint p4 = new Paint();
    Paint p5 = new Paint();

    int myData_x[] = new int[30000];
    int myData_y[] = new int[30000];
    int myData_color[] = new int[30000];

    static int radius = 15;

    int dataNumber = 0;
    int mx, my;
    static int whatColor = 0;


    // 생성자
    public MyView(Context context, AttributeSet attr){
        super(context);
        p1.setColor(Color.BLACK);
        p2.setColor(Color.RED);
        p3.setColor(Color.BLUE);
        p4.setColor(Color.YELLOW);
        p5.setColor(Color.GREEN);

        myData_x[0] = 0;
        myData_y[0] = 0;
        myData_color[0] = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 터치한 개수만큼 반복
        for (int i = 1; i <= dataNumber; i++){
            if (myData_color[i] == 0){
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p1);
            }

            if (myData_color[i] == 1){
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p2);
            }

            if (myData_color[i] == 2){
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p3);
            }

            if (myData_color[i] == 3){
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p4);
            }

            if (myData_color[i] == 4){
                canvas.drawCircle(myData_x[i], myData_y[i], radius, p5);
            }
        }
        invalidate();
    }// end of onDraw

    // 터치한 화면의 좌표와 색상을 저장
    public void saveData(){
        myData_x[dataNumber] = mx;
        myData_y[dataNumber] = my;
        myData_color[dataNumber] = whatColor;

    }
    public boolean onTouchEvent(MotionEvent event){
        // 터치한 좌표 확인
        mx = (int) event.getX();
        my = (int) event.getY();

        // 화면을 터치할 때 마다 값이 증가
        dataNumber += 1;
        saveData();
        return true;

    }
}
