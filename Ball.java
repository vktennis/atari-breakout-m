import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


//things to do : change radius, color, speed, move in a nonlinear fashion, etc.
//encode with an image
//have the ball not instantly break some bricks
//might have to change gamobject to double instaed of int
//change speeds maybe, for now keep it fast since I need to quickly test
public class Ball extends  GameObject{
    private double dX, dY;
    private boolean top;
    public Ball(boolean t){
        createBall(t);
    }
    private void createBall(boolean t) {
        top = t;
        loadImg();
        getImgDimensions();
        reset();
        resize(15,15);
    }
    private void loadImg(){ img = new ImageIcon("src/ball.png").getImage();}

    double getdX() { return dX; }
    double getdY() { return dY; };
    void setdX(double x) { this.dX = x; }
    void setdY(double y) { this.dY = y; }
    void scaleSpeed(double scale){
        if(dX<0) dX-=scale/Math.sqrt(2);
        else dX+=scale/Math.sqrt(2);
        if(dY<0) dY-=scale/Math.sqrt(2);
        else dY+=scale/Math.sqrt(2);
    }
    void multSpeed(double scale) {
        dX*=scale;
        dY*=scale;
    }
    void setNew(double angle, boolean bot) {
        double speed = Math.sqrt(dX*dX+dY*dY);
        if(bot){
            dX = speed*Math.sin(angle);
            dY = speed*-Math.cos(angle);
        }
        else{
            dX = speed*Math.sin(angle);
            dY = speed*Math.cos(angle);
        }
    }
    void update(){
        x+=dX;
        y+=dY;
        if(x<=-1.0*imgWidth/5) { setdX(-1.0*dX); }
        if(x>=Starter.SCREEN_WIDTH-1.0*imgWidth){ setdX(-1.0*dX); }
        if(y==0){ setdY(-1.0*dY); }
        if(y==Starter.HEIGHT){ setdY(-1.0*dY); }
    }
    private void reset(){
        if(top) {
            x = Starter.INIT_BTOP_X;
            y = Starter.INIT_BBOT_Y;
        }
        else {
            x = Starter.INIT_BALL_X;
            y = Starter.INIT_BALL_Y;
        }
        dX = -1.0*Math.random();
        dY = -1.0*Math.sqrt(4-dX*dX);
    }
}
