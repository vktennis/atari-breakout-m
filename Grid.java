import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.sql.Time;
import java.util.ArrayList;



public class Grid extends JPanel {
    private int numCoins = 0;
    private String message = "Good Game! Play again another time!";
    private Timer timer;
    private Ball ball;
    private Paddle topPaddle;
    private Paddle bottomPaddle;
    private boolean stillIn;
    private int numHit, numLives;
    private PauseButton pauseButton;
    private int currLevel;
    private int maxLevel;
    private ArrayList<Brick> bricks;
    private Rectangle bar;
    private Ball ball2;
    private Store store;

    public Grid(){
        createGrid();
    }
    private void createGrid(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Starter.WIDTH,Starter.HEIGHT));
        currLevel = 3;
        maxLevel = 4;
        startGame();
    }
    private void startNewLevel() {
        ++currLevel;
        bricks = new ArrayList<Brick>();
        ball = new Ball(false);
        if(currLevel>1) bottomPaddle.resize(bottomPaddle.getImgWidth()-20,bottomPaddle.getImgHeight());
        store = new Store();
        numLives = 3;
        numHit = 0;
        stillIn = true;
        int currType = 1;
        while(currType<=5){
           int xCoord = 0;
           int yCoord = 0;
           if(currLevel<3) yCoord = currType*35+110;
           else yCoord = currType*35+140;
           while(true) {
               int len = 50+(int) (Math.random()*25);
               if(xCoord+len<Starter.SCREEN_WIDTH) {
                   Brick temp;
                   if(currLevel==1) {
                       temp = new Brick(xCoord,yCoord,1,currType);
                   }
                   else {
                       temp = new Brick(xCoord,yCoord,(int) (Math.random()*(currLevel+1)+1),currType);
                   }
                   temp.resize(len,30);
                   bricks.add(temp);
                   xCoord += (len+5);
               }
               else {
                   break;
               }
           }
           ++currType;
        }
       if(currLevel>=3) {
           topPaddle = new Paddle(false,currLevel);
           topPaddle.resize(bottomPaddle.getImgWidth(),bottomPaddle.getImgHeight());
           topPaddle.setActivedX(bottomPaddle.getActivedX());
       }
       if(currLevel>=4) { ball2 = new Ball(true); }
        timer = new Timer(Starter.PERIOD, new GameCycle());
        timer.start();
    }
    private void startGame(){
        pauseButton = new PauseButton();
        store = new Store();
        bottomPaddle = new Paddle(true,1);
        bar = new Rectangle(Starter.SCREEN_WIDTH,0,30,Starter.HEIGHT);
        numCoins = 0;
        startNewLevel();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        if(stillIn) drawObjects(g2d);
        else gameFinished(g2d);
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawImg(Graphics2D g2d, Image img, double x, double y){
        AffineTransform t = new AffineTransform();
        t.translate(x,y);
        t.scale(1,1);
        g2d.drawImage(img,t,null);
    }
    private void drawObjects(Graphics2D g2d){
        drawImg(g2d,bottomPaddle.getImage(),bottomPaddle.getX(),bottomPaddle.getY());
        if(currLevel>=3) { drawImg(g2d,topPaddle.getImage(),topPaddle.getX(),topPaddle.getY()); }
        if(currLevel>=4) {drawImg(g2d,ball2.getImage(),ball2.getX(),ball2.getY()); }

        drawImg(g2d,ball.getImage(),ball.getX(),ball.getY());
        g2d.setColor(Color.WHITE);
        var font = new Font("Verdana",Font.BOLD,15);
        g2d.setFont(font);
        for(Brick b : bricks) {
            if(!b.getDestroyed()) {
//                System.out.println("bayus");
                drawImg(g2d,b.getImage(),b.getX(),b.getY());
                String put = ""+b.getHitPoints();
                g2d.drawString(put,(int) (b.getX()+b.getImgWidth()*1.0/2-10/2),(int) (b.getY()+b.getImgHeight()*1.0/2+10/2));
            }
        }
        g2d.fillRect(Starter.SCREEN_WIDTH,0,30,Starter.HEIGHT);

        String put = "Coins: " +numCoins;
        g2d.drawString(put,Starter.SCREEN_WIDTH+35,25);
        font = new Font("Verdana",Font.BOLD,11);
        g2d.setFont(font);
        put = "Press 'P' to Pause";
        g2d.drawString(put,Starter.SCREEN_WIDTH+35,Starter.HEIGHT-20);
        put = "<- and -> arrow keys = move bottom paddle";
        g2d.drawString(put,Starter.SCREEN_WIDTH+35,Starter.HEIGHT-40);
        if(currLevel>=3) {
            put = "'A' and 'D' = move top paddle";
            g2d.drawString(put,Starter.SCREEN_WIDTH+35,Starter.HEIGHT-60);
        }
        font = new Font("Verdana",Font.BOLD,20);
        put = "Level " + currLevel;
        g2d.setFont(font);
        g2d.drawString(put,Starter.SCREEN_WIDTH/2-30,25);
        put = "Powerups:";
        g2d.drawString(put,Starter.SCREEN_WIDTH+35,70);
        g2d.setColor(Color.RED);
        g2d.fillRect(Starter.SCREEN_WIDTH+35,80,285,60);
        g2d.fillRect(Starter.SCREEN_WIDTH+35,145,285,60);
        g2d.fillRect(Starter.SCREEN_WIDTH+35,210,285,60);
        font = new Font("Verdana",Font.BOLD,14);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        put = "Press '1' to decrease ball speed.";
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,105);
        put = "Cost: "+store.getC1();
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,125);
        put = "Press '2' to increase paddle length.";
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,170);
        put = "Cost: "+store.getC2();
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,190);
        put = "Press '3' to increase paddle speed.";
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,235);
        put = "Cost: "+store.getC3();
        g2d.drawString(put,Starter.SCREEN_WIDTH+40,255);
    }
    private void gameFinished(Graphics2D g2d){
        var font = new Font("Verdana",Font.BOLD,18);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(message,
                (Starter.WIDTH-fontMetrics.stringWidth(message))/2,
                Starter.HEIGHT/2);
    }
    private class TAdapter extends KeyAdapter{
        //encode top paddle
        @Override
        public void keyReleased(KeyEvent e) {
            if(currLevel>=3) {topPaddle.keyReleased(e); }
            bottomPaddle.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e){
            if(currLevel>=3) {topPaddle.keyPressed(e); }
            bottomPaddle.keyPressed(e);
            pauseButton.keyPressed(e);
            store.keyPressed(e);
        }
    }
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }
    private void doGameCycle() {
        if(pauseButton.getPaused()) { return; }
        if(store.getBallSpeedDecrease() && numCoins>=store.getC1()) {
            ball.multSpeed(0.75);
            if(currLevel>=4) { ball2.multSpeed(0.75); }
            numCoins-=store.getC1();
            store.setC1(store.getC1()+1);
        }
        if(store.getPadLengthIncrease() && numCoins>=store.getC2()) {
            if(currLevel>=3) {topPaddle.resize(topPaddle.getImgWidth()+20,topPaddle.getImgHeight()); }
            bottomPaddle.resize(bottomPaddle.getImgWidth()+20,bottomPaddle.getImgHeight());
            numCoins-=store.getC2();
            store.setC2(store.getC2()+1);
        }
        if(store.getPadSpeedIncrease() && numCoins>=store.getC3()) {
            if(currLevel>=3) { topPaddle.scaleSpeed(1.25); }
            bottomPaddle.scaleSpeed(1.25);
            numCoins-=store.getC3();
            store.setC3(store.getC3()+1);
        }
        store.setBallSpeedDecrease(false);
        store.setPadLengthIncrease(false);
        store.setPadSpeedIncrease(false);

        ball.update();
        bottomPaddle.update();
        if(currLevel>=3) { topPaddle.update(); }
        if(currLevel>=4) { ball2.update(); }
        checkCollision();
        repaint();
    }
    private void stopGame() {
        stillIn = false;
        timer.stop();
    }
    private void checkCollision() {
        if(ball.getRect().getMaxY()>Starter.BOTTOM_EDGE){
            --numLives;
            if(numLives == 0) stopGame();
            else {
                ball = new Ball(false);
                numHit = 5;
            }
        }
        if(currLevel>=4 && ball2.getRect().getMaxY()>Starter.BOTTOM_EDGE) {
            --numLives;
            if(numLives == 0) stopGame();
            else {
                ball2 = new Ball(true);
                numHit = 5;
            }
        }
        if(ball.getRect().getMinY()<Starter.TOP_EDGE) {
            if(currLevel>=3) {
                --numLives;
                if(numLives==0) stopGame();
                else {
                    ball = new Ball(false);
                    numHit = 5;
                }
            }
            else {
                ball.setdY(Math.abs(ball.getdY()));
            }
        }
        if(currLevel>=4 && ball2.getRect().getMinY()<Starter.TOP_EDGE) {
            --numLives;
            if(numLives==0) stopGame();
            else {
                ball2 = new Ball(true);
                numHit = 5;
            }
        }
        int safe = 0;
        for(Brick b : bricks) {
            if(b.getDestroyed()) ++ safe;
            if(safe==bricks.size()) {
                if(currLevel==maxLevel) {
                    message = "Congratulations! You beat this game and certified your proness!";
                    stopGame();
                }
                else{
                    startNewLevel();
                }
            }
        }
        //test this
        double MAXBOUNCEANGLE = 5 * Math.PI / 12;
        if ((ball.getRect()).intersects(bottomPaddle.getRect())) {
            double relativeIntersectX = (ball.getRect().getMinX()+ball.getRect().getMaxX())*1.0/2-(bottomPaddle.getX()+(bottomPaddle.getImgWidth()*1.0/2));
            double normalizedIntersectionX = (relativeIntersectX/(bottomPaddle.getImgWidth()*1.0/2));
            double bounceAngle = normalizedIntersectionX* MAXBOUNCEANGLE;
            bounceAngle = Math.max(bounceAngle,-1);
            bounceAngle = Math.min(bounceAngle,1);
            ball.setNew(bounceAngle,true);
        }
        if(currLevel >=3 && (ball.getRect()).intersects(topPaddle.getRect())){
            double relativeIntersectX = (ball.getRect().getMinX()+ball.getRect().getMaxX())*1.0/2-(topPaddle.getX()+(topPaddle.getImgWidth()*1.0/2));
            double normalizedIntersectionX = (relativeIntersectX/(topPaddle.getImgWidth()*1.0/2));
            double bounceAngle = normalizedIntersectionX* MAXBOUNCEANGLE;
            bounceAngle = Math.max(bounceAngle,-1);
            bounceAngle = Math.min(bounceAngle,1);
            ball.setNew(bounceAngle,false);
        }
        if(currLevel>=4 && (ball2.getRect().intersects(bottomPaddle.getRect()))) {
            double relativeIntersectX = (ball2.getRect().getMinX()+ball2.getRect().getMaxX())*1.0/2-(bottomPaddle.getX()+(bottomPaddle.getImgWidth()*1.0/2));
            double normalizedIntersectionX = (relativeIntersectX/(bottomPaddle.getImgWidth()*1.0/2));
            double bounceAngle = normalizedIntersectionX* MAXBOUNCEANGLE;
            bounceAngle = Math.max(bounceAngle,-1);
            bounceAngle = Math.min(bounceAngle,1);
            ball2.setNew(bounceAngle,true);
        }
        if(currLevel >=4 && (ball2.getRect()).intersects(topPaddle.getRect())){
            double relativeIntersectX = (ball2.getRect().getMinX()+ball2.getRect().getMaxX())*1.0/2-(topPaddle.getX()+(topPaddle.getImgWidth()*1.0/2));
            double normalizedIntersectionX = (relativeIntersectX/(topPaddle.getImgWidth()*1.0/2));
            double bounceAngle = normalizedIntersectionX* MAXBOUNCEANGLE;
            bounceAngle = Math.max(bounceAngle,-1);
            bounceAngle = Math.min(bounceAngle,1);
            ball2.setNew(bounceAngle,false);
        }
        //intersection at some bricks is bugged??
        for(Brick b : bricks) {
            if((ball.getRect()).intersects(b.getRect())) {
                double ballLeft = ball.getRect().getMinX();
                double ballHeight =  ball.getRect().getHeight();
                double ballWidth =  ball.getRect().getWidth();
                double ballTop =  ball.getRect().getMinY();

                var pointRight = new Point2D.Double(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point2D.Double(ballLeft - 1, ballTop);
                var pointTop = new Point2D.Double(ballLeft, ballTop - 1);
                var pointBottom = new Point2D.Double(ballLeft, ballTop + ballHeight + 1);
                if(!b.getDestroyed()) {
                    if (b.getRect().contains(pointRight)) { ball.setdX(-Math.abs(ball.getdX())); }
                    else if (b.getRect().contains(pointLeft)) { ball.setdX(Math.abs(ball.getdX())); }
                    if (b.getRect().contains(pointTop)) { ball.setdY(Math.abs(ball.getdY())); }
                    else if (b.getRect().contains(pointBottom)) { ball.setdY(-Math.abs(ball.getdY())); }
                    ++numHit;
                    ++numCoins;
                    if(numHit>0 && numHit%10==0) {
                        if(currLevel>=4) {
                            ball.scaleSpeed(1);
                            ball2.scaleSpeed(1);
                        }
                        else {
                            ball.scaleSpeed(2.0);
                        }
                    }
                    b.setHitPoints(b.getHitPoints()-1);
                    if(b.getHitPoints()==0) {
                        b.setDestroyed(true);
                    }
                }
            }
            if(currLevel>=4 && (ball2.getRect()).intersects(b.getRect())) {
                double ballLeft = ball2.getRect().getMinX();
                double ballHeight =  ball2.getRect().getHeight();
                double ballWidth =  ball2.getRect().getWidth();
                double ballTop =  ball2.getRect().getMinY();

                var pointRight = new Point2D.Double(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point2D.Double(ballLeft - 1, ballTop);
                var pointTop = new Point2D.Double(ballLeft, ballTop - 1);
                var pointBottom = new Point2D.Double(ballLeft, ballTop + ballHeight + 1);
                if(!b.getDestroyed()) {
                    if (b.getRect().contains(pointRight)) { ball2.setdX(-Math.abs(ball2.getdX())); }
                    else if (b.getRect().contains(pointLeft)) { ball2.setdX(Math.abs(ball2.getdX())); }
                    if (b.getRect().contains(pointTop)) { ball2.setdY(Math.abs(ball2.getdY())); }
                    else if (b.getRect().contains(pointBottom)) { ball2.setdY(-Math.abs(ball2.getdY())); }
                    ++numHit;
                    ++numCoins;
                    if(numHit>0 && numHit%10==0) {
                        ball.scaleSpeed(1);
                        ball2.scaleSpeed(1);
                    }
                    b.setHitPoints(b.getHitPoints()-1);
                    if(b.getHitPoints()==0) {
                        b.setDestroyed(true);
                    }
                }

            }
        }
    }
}

