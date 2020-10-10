import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.Timer;


public class GameRunner {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setTitle("Atari Breakout Modified! By Vedant Kumud");
                frame.getContentPane().setBackground(Color.BLACK);
                frame.add(new Grid());
                frame.pack();

                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}