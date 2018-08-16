package github.bbubbush.com.basicsum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    TextView t1;
    Button b1;
    String input1, input2;
    int sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = (EditText) findViewById(R.id.e1);  // 첫번째 숫자 입력
        e2 = (EditText) findViewById(R.id.e2);  // 두번째 숫자 입력
        t1 = (TextView) findViewById(R.id.t1);  // 정답보여주는 화면
        b1 = (Button) findViewById(R.id.b1);    // 정답 확인 버튼

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                input1 = e1.getText().toString();
                input2 = e2.getText().toString();
                sum = Integer.parseInt(input1) + Integer.parseInt(input2);
                t1.setText(Integer.toString(sum));
            }
        });
    }
}
