package fragment.techtown.android.samplefragment2;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ColorListFragment extends ListFragment {

    /*2*/
    /*OnColorSelectedListener를 외부(activity)에서 지정할 수 있도록 -> 액티비티와 연결이 될 수 있도록*/
    private OnColorSelectedListener mListener;
    /*1*/
    interface OnColorSelectedListener{
        void onColorSelected(int color);
    }

    /*3*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener =  (OnColorSelectedListener) context;
        }catch (Exception e){
            throw new ClassCastException(((Activity) context).getLocalClassName()+"는 OnColorSelectedListener를 구현해야                                                                                          합니다....");
        }


    }
    /*4*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) l.getAdapter();
        String colorString = adapter.getItem(position);
        int color = Color.RED;

        switch (colorString){
            case "Red":
                color = Color.RED;
                break;
            case "Green":
                color = Color.GREEN;
                break;
            case "Blue":
                color = Color.BLUE;
                break;
        }
        if(mListener != null){
            mListener.onColorSelected(color);
        }
    }

    /*플래그먼트의 뷰를 만들어주는 메서드*/
    /*ListFragment를 상속 받았을 시에는 이미 뷰가 완성이 된 상태이다*/
    /*Fragment를 상속받았을때는 만들어진 뷰가 없기에 뷰를 먼저 만드는 것이 우선순위이다.*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> colorList = Arrays.asList("Red", "Green", "Blue");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, colorList);

        setListAdapter(adapter);
        /*이렇게하면 리스트뷰가 내장된 뷰를 쉽게 만들 수 있다.*/

    }
}
