package github.bbubbush.com.gamebasic;

public class MyMissile {
    int x, y;
    int missileSpeed = 35;
    MyMissile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(){
        y -= missileSpeed;
    }
}
