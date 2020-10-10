import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class GameObject {
    double x;
    double y;
    int imgWidth, imgHeight;
    Image img;
    double getX(){ return x;}
    double getY(){ return y;}
    protected void setX(double x){this.x = x;}
    protected void setY(double y){this.y = y;}
    int getImgHeight() { return imgHeight;}
    int getImgWidth() { return imgWidth;}
    Image getImage() { return img;}
    Rectangle2D.Double getRect() { return new Rectangle2D.Double(x,y,img.getWidth(null)*1.0,img.getHeight(null)*1.0);}
    void getImgDimensions() {
        imgWidth = img.getWidth(null);
        imgHeight = img.getHeight(null);
    }
    public void resize(int newWidth, int newHeight) {
        imgWidth = newWidth;
        imgHeight = newHeight;
        img = img.getScaledInstance(newWidth,newHeight,Image.SCALE_SMOOTH);
    }
}
