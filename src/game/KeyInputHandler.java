package game;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyInputHandler (GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed (KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_P -> {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                }
                else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
            }
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

    @Override
    public void keyTyped (KeyEvent keyEvent) {
        // Not used.
    }
}
