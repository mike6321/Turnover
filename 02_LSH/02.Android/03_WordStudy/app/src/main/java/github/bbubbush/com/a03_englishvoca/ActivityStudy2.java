package github.bbubbush.com.a03_englishvoca;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityStudy2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordtest);
        

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
			StudyView2.soundOk=1;
            break;
        case 2:
			StudyView2.soundOk=0;
             break;
        case 3:
            break;
        case 4:
        }
        return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		if(keyCode== KeyEvent.KEYCODE_BACK) {
			System.exit(0);
			return false;
		}
		return false;
	}  
}

