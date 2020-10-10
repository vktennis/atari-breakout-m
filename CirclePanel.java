/*
  Author: Vedant Kumud (vkgainz)
 */

import java.awt.*;
import javax.swing.*;

public class CirclePanel extends JPanel {
    public CirclePanel(){
        setPreferredSize(new Dimension(100,100));
        setBackground(Color.white);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
    public void drawCircle(Graphics g, int cX, int cY, int radius){
        g.drawOval(cX-radius, cY-radius, 2*radius, 2*radius);
    }
}
