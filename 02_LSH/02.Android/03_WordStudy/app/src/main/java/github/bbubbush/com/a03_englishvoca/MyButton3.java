package github.bbubbush.com.a03_englishvoca;   // 토익단어 공부용 버튼

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyButton3 {
    public int x, y;
    public int w, h;

    public Bitmap button_img;

    private Bitmap buttonImage[] = new Bitmap[2];
    public int whichPic;

    public MyButton3(int x, int y, int z, int kind) {
        this.x = x;
        this.y = y;
        this.whichPic = z;


        if(kind==1)
            for (int i = 0; i < 2; i++) {
                buttonImage[i] = BitmapFactory.decodeResource(StudyView3.mContext.getResources(), R.drawable.toeic00 + whichPic * 2 + i);

                //이전, 다음, 단어선택, 내노트, 랜덤, 나가기 버튼 및 단어선택에 나오는 8개 버튼들
                if (whichPic <60) {  //15-22 submenu
                    int xWidth = StudyView3.Width / 11;
                    int yWidth = xWidth;

                    buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
                }
            }


        if(kind==0)
        for (int i = 0; i < 2; i++) {
            buttonImage[i] = BitmapFactory.decodeResource(StudyView3.mContext.getResources(), R.drawable.word00 + whichPic * 2 + i);

            //이전, 다음, 단어선택, 내노트, 랜덤, 나가기 버튼 및 단어선택에 나오는 8개 버튼들
            if (whichPic < 7 ) {  //15-22 submenu
                int xWidth = StudyView3.Width / 11;
                int yWidth = xWidth;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //다음문제, 다시풀기 버튼 아이콘, 카카오톡 보내기 아이콘, 단어장등록 아이콘
            if (whichPic == 12 || whichPic == 13 || whichPic == 33 || whichPic == 23) {
                int xWidth = StudyView3.Width / 5;
                int yWidth = StudyView3.Height / 7;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //객관식 선택 버튼 1에서 5번까지.  본 예제에서는 1~4번 까지 사용함
            if (whichPic >= 7 && whichPic <= 10) {
                int xWidth = StudyView3.Width / 16;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);

            }

            //해설버튼
            if (whichPic == 31) {
                int xWidth = StudyView3.Width / 13;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //25: 결과확인 버튼, 32 결과보기 버튼 이미지
            if (whichPic == 25 || whichPic == 32) {
                int xWidth = StudyView3.Width / 5;
                int yWidth = StudyView3.Height / 7;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //평가결과보기에서 왼쪽, 오른쪽 버튼 이미지, 닫기 버튼 , 전체 삭제버튼
            if (whichPic == 26 || whichPic == 27 || whichPic == 28 ||  whichPic == 30) {
                int xWidth = StudyView3.Width / 11;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //단어 삭제버튼
            if (whichPic == 29 ) {
                int xWidth = StudyView3.Width / 16;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);

            }

        }

        w = buttonImage[0].getWidth() / 2;
        h = buttonImage[0].getHeight() / 2;
        button_img = buttonImage[0];

    }

    public boolean btn_released() {
        button_img = buttonImage[0];
        return true;
    }

    public boolean btn_press() {
        button_img = buttonImage[1];
        return true;
    }

}
