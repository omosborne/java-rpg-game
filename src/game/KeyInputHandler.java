package game;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {
    @Override
    public void keyTyped (KeyEvent keyEvent) {}

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed (KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
        }
    }

    @Override
    public void keyReleased (KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}
