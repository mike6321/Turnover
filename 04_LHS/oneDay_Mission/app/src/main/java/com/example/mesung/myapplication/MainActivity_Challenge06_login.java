package com.example.mesung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_Challenge06_login extends AppCompatActivity {
    EditText idValue = null;
    EditText pwdValue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge06_login);

        Intent intent = getIntent();

        if(intent.getStringExtra("customerPage") != null){
            Toast.makeText(this, intent.getStringExtra("customerPage"), Toast.LENGTH_LONG).show();
        }else if(intent.getStringExtra("salePage") != null){
            Toast.makeText(this, intent.getStringExtra("salePage"), Toast.LENGTH_LONG).show();
        }else if(intent.getStringExtra("itemPage") != null){
            Toast.makeText(this, intent.getStringExtra("itemPage"), Toast.LENGTH_LONG).show();
        }


        Button login = (Button) findViewById(R.id.login);
        idValue = (EditText) findViewById(R.id.idValue);
        pwdValue = (EditText) findViewById(R.id.pwdValue);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity_Challenge06_login.this, idValue.getText(), Toast.LENGTH_SHORT).show();
                if(idValue.getText().toString().equals("")){
                    Toast.makeText(MainActivity_Challenge06_login.this, "아이디를 입력해주세욥.", Toast.LENGTH_SHORT).show();
                } else if(pwdValue.getText().toString().equals("")){
                    Toast.makeText(MainActivity_Challenge06_login.this, "비번을 입력해주세욥.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_menu.class);
                    intent.putExtra("loginPage", "로그인 페이지에서 이동");
                    startActivityForResult(intent, 3000);
                }
            }
        });
    }
}