package com.example.mesung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_Challenge06_menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge06_menu);

        Intent intent = getIntent();



        if(intent.getStringExtra("loginPage") != null){
            Toast.makeText(this, intent.getStringExtra("loginPage"), Toast.LENGTH_LONG).show();
        }else if(intent.getStringExtra("customerPage") != null){
            Toast.makeText(this, intent.getStringExtra("customerPage"), Toast.LENGTH_LONG).show();
        }else if(intent.getStringExtra("salePage") != null){
            Toast.makeText(this, intent.getStringExtra("salePage"), Toast.LENGTH_LONG).show();
        }else if(intent.getStringExtra("itemPage") != null){
            Toast.makeText(this, intent.getStringExtra("itemPage"), Toast.LENGTH_LONG).show();
        }


        Button customer = (Button) findViewById(R.id.customer);
        Button item = (Button) findViewById(R.id.item);
        Button sale = (Button) findViewById(R.id.sale);


        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_Customer.class);
                intent.putExtra("menuPage", "메뉴페이지에서 이동");
                startActivity(intent);
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_Item.class);
                intent.putExtra("menuPage", "메뉴페이지에서 이동");
                startActivity(intent);
            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Challenge06_Sale.class);
                intent.putExtra("menuPage", "메뉴페이지에서 이동");
                startActivity(intent);
            }
        });

    }
}
