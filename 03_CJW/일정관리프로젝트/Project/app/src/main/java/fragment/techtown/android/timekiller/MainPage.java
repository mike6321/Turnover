package fragment.techtown.android.timekiller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:
                show();
                return true;
            case R.id.action_search:
                show2();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*http://webnautes.tistory.com/1094*/
    /*다이얼 로그 만들기*/
    void show(){
        /*노트 문서 리스트*/
        final List<String> ListItems = new ArrayList<String>();
        ListItems.add("문서");
        ListItems.add("체크리스트");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("노트 추가");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                     String selectedText = items[pos].toString();
                     Toast.makeText(MainPage.this, selectedText, Toast.LENGTH_LONG).show();
                     /*문서를 클릭하였을때*/
                     if(pos==0){
                         Intent intent = new Intent(getApplicationContext(), WriteNotePage.class);
                         startActivity(intent);
                     /*체크리스트를 클릭하였을때*/
                     }else if(pos==1){
                         /*체크리스트는 아직 미정*/
                     }
            }
        });
        builder.show();
    }

    void show2(){

    }

    /*리사이클러 뷰 사용*/
    /*http://dudmy.net/android/2017/06/23/consider-of-recyclerview/*/


    /*public void popUpOpen(View view){
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "Test Popup");
        startActivityForResult(intent,1);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(requestCode==RESULT_OK){
                String result = data.getStringExtra("result");

            }
        }
    }*/
}
