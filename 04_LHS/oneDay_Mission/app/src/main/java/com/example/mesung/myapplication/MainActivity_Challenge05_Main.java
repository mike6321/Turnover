package com.example.mesung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity_Challenge05_Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge05_main);

        final Button customer = (Button) findViewById(R.id.customer);
        Button item = (Button) findViewById(R.id.item);
        Button sale = (Button) findViewById(R.id.sale);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge05_Login.class);
                Toast.makeText(MainActivity_Challenge05_Main.this, customer.getText(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge05_Login.class);
                Toast.makeText(MainActivity_Challenge05_Main.this, "상품관리", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge05_Login.class);
                Toast.makeText(MainActivity_Challenge05_Main.this, "판매관리", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}