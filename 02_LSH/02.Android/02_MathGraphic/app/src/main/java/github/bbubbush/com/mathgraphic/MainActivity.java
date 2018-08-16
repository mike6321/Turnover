package github.bbubbush.com.mathgraphic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increaseValue(View v){
        MyView.radius += 2;
    }

    public void decreaseValue(View v){
        MyView.radius -= 2;
    }

    public void setRed(View v){
        MyView.whatColor = 1;
    }

    public void setBlue(View v){
        MyView.whatColor = 2;
    }

    public void setYellow(View v){
        MyView.whatColor = 3;
    }

    public void setGreen(View v){
        MyView.whatColor = 4;
    }

    public void setBlack(View v){
        MyView.whatColor = 0;
    }

}
