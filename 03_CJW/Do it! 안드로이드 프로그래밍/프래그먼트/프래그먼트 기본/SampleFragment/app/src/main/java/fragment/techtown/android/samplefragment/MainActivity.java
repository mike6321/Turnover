package fragment.techtown.android.samplefragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*fragment태그에 해당 뷰가 들어가게 된다*/
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);

        menuFragment = new MenuFragment();
    }

    public void onFragmentChanged(int index){
        if(index==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,menuFragment).commit();
        }else if(index==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
        }
    }
}
