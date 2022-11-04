package game;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {

    private final GamePanel gp;

    private boolean upPressed;
    private boolean leftPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private boolean shiftPressed;

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

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public KeyInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch (gp.getGameState()) {
            case PLAY -> checkPlayKeysPressed(keyCode);
            case PAUSE -> checkPauseKeysPressed(keyCode);
            case DIALOGUE -> checkDialogueKeysPressed(keyCode);
            case TITLE -> checkTitleKeysPressed(keyCode);
        }
    }

    private void checkPlayKeysPressed(int keyCode) {
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
        } else if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        } else if (keyCode == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
    }

    private void checkPauseKeysPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_P) {
            togglePause();
        }
    }

    private void checkDialogueKeysPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            gp.setGameState(GamePanel.State.PLAY);
        }
    }

    private void checkTitleKeysPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            gp.getGameUI().selectPreviousTitleScreenOption();
        } else if (keyCode == KeyEvent.VK_S) {
            gp.getGameUI().selectNextTitleScreenOption();
        } else if (keyCode == KeyEvent.VK_ENTER) {
            switch (gp.getGameUI().getSelectedTitleScreenOption()) {
                case NEW_GAME -> gp.setGameState(GamePanel.State.PLAY);
                case QUIT -> System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (gp.getGameState().isPlay()) {
            if (keyCode == KeyEvent.VK_W) {
                upPressed = false;
            } else if (keyCode == KeyEvent.VK_A) {
                leftPressed = false;
            } else if (keyCode == KeyEvent.VK_S) {
                downPressed = false;
            } else if (keyCode == KeyEvent.VK_D) {
                rightPressed = false;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                enterPressed = false;
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.setGameState(GamePanel.State.TITLE);
            } else if (keyCode == KeyEvent.VK_SHIFT) {
                shiftPressed = false;
            }
        }
    }

    private void togglePause() {
        if (gp.getGameState().isPlay()) {
            gp.setGameState(GamePanel.State.PAUSE);
        }
        else if (gp.getGameState().isPause()) {
            gp.setGameState(GamePanel.State.PLAY);
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
