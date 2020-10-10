import java.awt.event.KeyEvent;
import javax.swing.*;
import java.security.Key;
import java.util.*;

public class Paddle extends GameObject {

    private double dx;
    private double activedX;
    private boolean bot;
    public Paddle(boolean b,int level) {
        createPaddle(b, level);
    }
    private void createPaddle(boolean b,int level){
        loadImg();
        getImgDimensions();
        this.bot = b;
        activedX = 3;
        reset();
        resize(150-20*(level-1),10);
    }
    private void loadImg(){ img = new ImageIcon("src/paddle.png").getImage(); }
    void update(){
        x += dx;
        x=  Math.max(x,0);
        x = Math.min(x,Starter.SCREEN_WIDTH-imgWidth);
    }
    void setActivedX(double x) { activedX = x; }
    double getActivedX() { return activedX; }
    void scaleSpeed(double x) { activedX*=x; }
    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (bot) {
            if (key == KeyEvent.VK_LEFT) {
                dx = -activedX;
            }
            if (key == KeyEvent.VK_RIGHT) {
                dx = activedX;
            }
        } else {
            if (key == KeyEvent.VK_A) {
                dx = -activedX;
            }
            if (key == KeyEvent.VK_D) {
                dx = activedX;
            }
        }
    }
    void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if (bot) {
            if(key == KeyEvent.VK_LEFT) { dx = 0; }
            if(key == KeyEvent.VK_RIGHT) { dx = 0; }
        }
        else{
            if(key == KeyEvent.VK_A) { dx = 0; }
            if(key == KeyEvent.VK_D) { dx = 0; }
        }
    }
    void reset() {
        if(bot){
            x = Starter.INIT_BOT_X;
            y = Starter.INIT_BOT_Y;
        }
        else{
            x = Starter.INIT_TOP_X;
            y = Starter.INIT_TOP_Y;
        }
    }

}
