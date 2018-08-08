package com.example.mesung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity_Challenge06_Customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge06_customer);

        Intent intent = getIntent();
        Toast.makeText(MainActivity_Challenge06_Customer.this, intent.getStringExtra("menuPage"), Toast.LENGTH_LONG).show();

        Button gotoMenu = (Button) findViewById(R.id.gotomenu);
        Button gotoLogin = (Button) findViewById(R.id.gotologin);

        gotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_menu.class);
                intent.putExtra("customerPage", "고객 관리 페이지에서 이동");
                startActivity(intent);
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_login.class);
                intent.putExtra("customerPage", "고객 관리 페이지에서 이동");
                startActivity(intent);
            }
        });
    }
}
