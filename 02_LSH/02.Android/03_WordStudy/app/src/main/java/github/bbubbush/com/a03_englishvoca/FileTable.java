package github.bbubbush.com.a03_englishvoca;

import java.io.IOException;
import java.io.InputStream;

public class FileTable {

    InputStream fi;

    FileSplit0 word;
    FileSplit1 word2;

    // 단어공부 파일 읽어오기
    public void loadFile(int num) {

        fi = StudyView.mContext.getResources().openRawResource(R.raw.high01 + num-1);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word = new FileSplit0(s);

        } catch (IOException e) {
        }

    }

    //for test
    public void loadFile2(int num) {

        InputStream fi = StudyView2.mContext.getResources().openRawResource(R.raw.test01 + num);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word2 = new FileSplit1(s);
        } catch (IOException e) {
        }
    }

}
