package fragment.techtown.android.samplefragment2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*OnColorSelectedListener를 implements하지 않으면 에러가 발생*/
public class MainActivity extends AppCompatActivity implements ColorListFragment.OnColorSelectedListener {

    private ColorFragment mcolorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*프래그먼트를 가지고 올때는 findViewById로는 가져올 수가 없다 (뷰가 아니기때문에)*/
        mcolorFragment = (ColorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_color);

        //mcolorFragment.setColor(Color.RED);

        /*이제 뭘해볼까 준우야  버튼 클릭하면 아래 색이 바뀌게 한번해보자 시볼러마*/

    }

    @Override
    public void onColorSelected(int color) {
        mcolorFragment.setColor(color);
    }
}
