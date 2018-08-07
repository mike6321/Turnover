package com.example.mesung.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_Challenge04 extends AppCompatActivity {
    TextView textView3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge04);

        Button button1 = (Button) findViewById(R.id.spend);
        textView3 = (TextView) findViewById(R.id.content);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity_Challenge04.this, textView3.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
