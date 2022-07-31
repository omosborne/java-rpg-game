package game;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {

    private final GamePanel gp;

    private boolean upPressed;
    private boolean leftPressed;
    private boolean downPressed;
    private boolean rightPressed;

    public boolean isUpLeftPressed() {
        return upPressed && leftPressed;
    }

    public boolean isLeftDownPressed() {
        return leftPressed && downPressed;
    }

    public boolean isDownRightPressed() {
        return downPressed && rightPressed;
    }

    public boolean isRightUpPressed() {
        return rightPressed && upPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public KeyInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = true;
        } else if (keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        } else if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        } else if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        } else if (keyCode == KeyEvent.VK_P) {
            togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = false;
        } else if (keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        } else if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    private void togglePause() {
        if (gp.isInPlayState()) {
            gp.setGameState(GamePanel.PAUSE_STATE);
        }
        else if (gp.isInPauseState()) {
            gp.setGameState(GamePanel.PLAY_STATE);
        }
    }

    public boolean noDirectionKeysPressed() {
        return !(upPressed || leftPressed || downPressed || rightPressed);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // Not used.
    }
}
