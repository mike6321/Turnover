package github.bbubbush.com.a03_englishvoca;   //토익 단어 공부용

import java.io.IOException;
import java.io.InputStream;

public class FileTable2 {

    InputStream fi;

    FileSplit0 word;

    // 단어공부 파일 읽어오기
    public void loadFile(int num) {

        fi = StudyView3.mContext.getResources().openRawResource(R.raw.toeic01 + num-1);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word = new FileSplit0(s);

        } catch (IOException e) {
        }

    }

}
