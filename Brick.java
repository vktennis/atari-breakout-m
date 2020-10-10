import javax.swing.*;
import java.awt.*;

public class Brick extends GameObject {
    private boolean destroyed;
    private int hitPoints;
    private int type;
    public Brick(int x,int y,int hp,int t) {
        createBrick(x,y,hp,t);
    }
    private void createBrick(int x,int y,int hp,int t ){
        this.x = x;
        this.y = y;
        this.hitPoints = hp;
        this.type = t;
        destroyed = false;
        loadImg();
        getImgDimensions();
    }
    private void loadImg(){

        String pref = "src/";
        String suf = "brick.png";
        String fileName = "";
        if(type==1) { fileName = pref+"blue"+suf; }
        if(type==2) { fileName = pref+"green"+suf; }
        if(type==3) { fileName = pref+"orange"+suf; }
        if(type==4) { fileName = pref+"purple"+suf; }
        if(type==5) { fileName = pref+"red"+suf; }
        img = new ImageIcon(""+fileName).getImage();
    }
    boolean getDestroyed() { return destroyed; }
    int getHitPoints() { return hitPoints; }
    void setDestroyed(boolean val) { destroyed = val; }
    void setHitPoints(int hp) { hitPoints = hp; }
}
