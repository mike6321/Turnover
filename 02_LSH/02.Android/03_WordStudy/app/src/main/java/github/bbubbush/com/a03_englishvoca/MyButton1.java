package github.bbubbush.com.a03_englishvoca;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyButton1 {
    public int x, y;
    public int w, h;
    public Bitmap button_img;
    private Bitmap buttonImage[] = new Bitmap[2];
    public int whichPic;

    public MyButton1(int x, int y, int z) {    //  studyview
        this.x = x;
        this.y = y;
        this.whichPic = z;

        for (int i = 0; i < 2; i++) {
            buttonImage[i] = BitmapFactory.decodeResource(StudyView.mContext.getResources(), R.drawable.word00 + whichPic * 2 + i);
            
            //이전, 다음, 단어선택, 내노트, 랜덤, 나가기 버튼 및 단어선택에 나오는 8개 버튼들
            if (whichPic < 8 || (whichPic >= 15 && whichPic <= 22)) {  //15-22 submenu
                int xWidth = StudyView.Width / 11;
                int yWidth = xWidth;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //객관식 문제 선택버튼 1,2,3,4
            if (whichPic > 6 && whichPic < 12) {
                int xWidth = StudyView.Width / 16;
                int yWidth = xWidth;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //다음문제, 다시풀기 버튼 아이콘, 카카오톡 보내기 아이콘, 단어장등록 아이콘
            if (whichPic == 12 || whichPic == 13 || whichPic == 33 || whichPic == 23) {
                int xWidth = StudyView.Width / 5;
                int yWidth = StudyView.Height / 7;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

             //확인하기 버튼 : 평가 에서 사용됨
            if (whichPic == 25) {
                int xWidth = StudyView.Width / 5;
                int yWidth = StudyView.Height / 7;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

// 내사전에서 왼쪽, 오른쪽 버튼
            if (whichPic == 26 || whichPic == 27 || whichPic == 28) {    //단어장에서 삭제버튼  word58.png word59.png
                int xWidth = StudyView.Width / 16;
                int yWidth = xWidth;

                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }


            if (whichPic == 29) {    //단어장에서 삭제버튼  word58.png word59.png
                int xWidth = StudyView.Width / 16;
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

    //버튼이 눌리면 나오는 이미지
    public boolean btn_press() {

        button_img = buttonImage[1];

        return true;
    }

}
