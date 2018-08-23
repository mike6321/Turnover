package github.bbubbush.com.a03_englishvoca;   //평가

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyButton2 {
    public int x, y;
    public int w, h;

    public Bitmap button_img;
    public int pressed = 0;

    private Bitmap buttonImage[] = new Bitmap[2];
    public int whichPic;

    public MyButton2(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.whichPic = z;

        for (int i = 0; i < 2; i++) {
            buttonImage[i] = BitmapFactory.decodeResource(StudyView2.mContext.getResources(), R.drawable.word00 + whichPic * 2 + i);

            //나가기 버튼
            if (whichPic == 5) {
                int xWidth = StudyView2.Width / 11;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);

            }

            //객관식 선택 버튼 1에서 5번까지.  본 예제에서는 1~4번 까지 사용함
            if (whichPic >= 7 && whichPic <= 11) {
                int xWidth = StudyView2.Width / 16;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);

            }

            //다음문제 버튼
            if (whichPic == 12) {
                int xWidth = StudyView2.Width / 5;
                int yWidth = StudyView2.Height / 7;
                buttonImage[0] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //해설버튼
            if (whichPic == 31) {
                int xWidth = StudyView2.Width / 13;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //25: 결과확인 버튼, 32 결과보기 버튼 이미지
            if (whichPic == 25 || whichPic == 32) {
                int xWidth = StudyView2.Width / 5;
                int yWidth = StudyView2.Height / 7;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

            //평가결과보기에서 왼쪽, 오른쪽 버튼 이미지, 닫기 버튼 , 전체 삭제버튼
            if (whichPic == 26 || whichPic == 27 || whichPic == 28 ||  whichPic == 30) {
                int xWidth = StudyView2.Width / 11;
                int yWidth = xWidth;
                buttonImage[i] = Bitmap.createScaledBitmap(buttonImage[i], xWidth, yWidth, true);
            }

        }

        w = buttonImage[0].getWidth() / 2;
        h = buttonImage[0].getHeight() / 2;
        button_img = buttonImage[0];

    }

    public MyButton2(int x, int y, int z, int w) {
        // TODO Auto-generated constructor stub
        this.x = x;
        this.y = y;
        this.whichPic = z;


        //문제수 버튼 10, 20, 25, 50, 100문제
        buttonImage[0] = BitmapFactory.decodeResource(StudyView2.mContext.getResources(), R.drawable.test00 + whichPic);

        if (whichPic>=5 || whichPic <= 9) {

            int xWidth = StudyView2.Width / 11;
            int yWidth = xWidth;

            buttonImage[0] = Bitmap.createScaledBitmap(buttonImage[0], xWidth, yWidth, true);

        }

        //설정버튼
        if (whichPic == 14) {

            int xWidth = StudyView2.Width / 11;
            int yWidth = xWidth;

            buttonImage[0] = Bitmap.createScaledBitmap(buttonImage[0], xWidth, yWidth, true);

        }

        this.w = buttonImage[0].getWidth() / 2;
        this.h = buttonImage[0].getHeight() / 2;
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
