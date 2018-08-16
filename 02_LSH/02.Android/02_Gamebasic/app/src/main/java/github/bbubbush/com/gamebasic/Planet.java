package github.bbubbush.com.gamebasic;

public class Planet {
    int x, y;
    int planetSpeed = 15;
    int dir;    // 행성 이동 방향

    Planet(int x, int y){
        this.x = x;
        this.y = y;
        this.dir = (int)(Math.random() * 2);
    }

    Planet(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void move(){
        if(dir == 0){
            x -= planetSpeed;
        }else{
            x += planetSpeed;
        }
    }
}
