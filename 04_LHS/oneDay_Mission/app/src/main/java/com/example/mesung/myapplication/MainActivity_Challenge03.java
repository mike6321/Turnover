package com.example.mesung.myapplication;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity_Challenge03 extends AppCompatActivity {
    ImageView imageView1 = null;
    ImageView imageView2 = null;
    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge03);

        Button button1 = (Button)findViewById(R.id.upClicked);
        Button button2 = (Button)findViewById(R.id.downClicked);

        imageView1 = (ImageView) findViewById(R.id.upImage);
        imageView2 = (ImageView) findViewById(R.id.downImage);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 1){
                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ci_tab02_img02_0));
                    imageView1.setImageDrawable(getResources().getDrawable(R.drawable.logo_homepage));
                    index = 2;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 2){
                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.logo_homepage));
                    imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ci_tab02_img02_0));
                    index = 1;
                }
            }
        });
    }


}
