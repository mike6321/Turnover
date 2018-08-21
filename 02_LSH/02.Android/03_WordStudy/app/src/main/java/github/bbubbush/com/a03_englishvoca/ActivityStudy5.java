package github.bbubbush.com.a03_englishvoca; //토플, 텝스, 공무원 심화 단어 공부

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityStudy5 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordstudy4);
    }

	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "sound on");
		menu.add(0, 2, 0, "sound off");
		menu.add(0, 3, 0, "");
		menu.add(0, 4, 0, "");
		return true;
	}

	//-------------------------------------
	//  onOptions ItemSelected
	//-------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case 1:
			StudyView5.soundOk=1;
             break;
        case 2:
			StudyView5.soundOk=0;
             break;
        case 3:
            break;
        case 4:
			break;
        }
        return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		if(keyCode== KeyEvent.KEYCODE_BACK) {
			System.exit(0);   //메인화면으로 돌아가기
			return false;
		}
	
		return false;
	}  
}

