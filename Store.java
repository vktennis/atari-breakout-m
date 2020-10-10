import java.awt.event.KeyEvent;

public class Store {
    private boolean padLengthIncrease; //increase paddle length
    private boolean padSpeedIncrease; //increase paddle speed
    private boolean ballSpeedDecrease; //decrease ball speed
    private int c1, c2, c3; //stores the number of coins needed for operations
    public Store() { createStore(); }
    private void createStore() {
        padLengthIncrease = false;
        padSpeedIncrease = false;
        ballSpeedDecrease = false;
        c1 = 3;
        c2 = 3;
        c3 = 3;
    }
    int getC1() { return c1; }
    int getC2() { return c2; }
    int getC3() { return c3; }
    boolean getPadLengthIncrease() { return padLengthIncrease; }
    boolean getPadSpeedIncrease() { return padSpeedIncrease; }
    boolean getBallSpeedDecrease() { return ballSpeedDecrease; }
    void setC1(int x) { c1 = x; }
    void setC2(int x) { c2 = x; }
    void setC3(int x) { c3 = x; }
    void setPadLengthIncrease(boolean b) { padLengthIncrease = b; }
    void setPadSpeedIncrease(boolean b) { padSpeedIncrease = b; }
    void setBallSpeedDecrease(boolean b) { ballSpeedDecrease = b; }

    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_1) {
            ballSpeedDecrease = true;
        }
        if(key==KeyEvent.VK_2) {
            padLengthIncrease = true;
        }
        if(key==KeyEvent.VK_3) {
            padSpeedIncrease = true;
        }
    }

}
