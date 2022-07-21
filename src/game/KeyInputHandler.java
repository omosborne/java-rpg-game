package game;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {
    @Override
    public void keyTyped (KeyEvent keyEvent) {}
    public boolean isWalking = false;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed (KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> {
                isWalking = true;
                upPressed = true;
            }
            case KeyEvent.VK_S -> {
                isWalking = true;
                downPressed = true;
            }
            case KeyEvent.VK_A -> {
                isWalking = true;
                leftPressed = true;
            }
            case KeyEvent.VK_D -> {
                isWalking = true;
                rightPressed = true;
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> {
                isWalking = false;
                upPressed = false;
            }
            case KeyEvent.VK_S -> {
                isWalking = false;
                downPressed = false;
            }
            case KeyEvent.VK_A -> {
                isWalking = false;
                leftPressed = false;
            }
            case KeyEvent.VK_D -> {
                isWalking = false;
                rightPressed = false;
            }
        }
    }
}
