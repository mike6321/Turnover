package fragment.techtown.android.timekiller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class WriteNotePage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_writepage);*/
        setContentView(R.layout.activity_writepage);
        Spinner selectcolor = (Spinner) findViewById(R.id.spinnercolor);
        ArrayAdapter colorAdapter = ArrayAdapter.createFromResource(this, R.array.select_color, android.R.layout
                .simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectcolor.setAdapter(colorAdapter);

        /*셀렉트박스 클릭 시 색깔 변경*/
        selectcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        findViewById(R.id.backgrd).setBackgroundColor(Color.rgb(255,204,255));
                        break;
                    case 1 :
                        findViewById(R.id.backgrd).setBackgroundColor(Color.rgb(000,255,255));
                        break;
                    case 2 :
                        findViewById(R.id.backgrd).setBackgroundColor(Color.rgb(153,204,255));
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
