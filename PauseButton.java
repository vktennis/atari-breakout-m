import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.concurrent.atomic.AtomicBoolean;

public class PauseButton {
    private boolean paused;
    public PauseButton() {
        paused = false;
    }
    boolean getPaused() { return paused; }
    void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key== KeyEvent.VK_P) { paused = !paused; }
    }
}
