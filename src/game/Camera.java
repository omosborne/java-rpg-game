package game;

import game.entity.Player;

public class Camera {

    private final Player player;

    private int cameraFrameLeft;
    private int cameraFrameRight;
    private int cameraFrameTop;
    private int cameraFrameBottom;

    public Camera(GamePanel gp) {
        player = gp.getPlayer();
    }

    private void getCameraFrame() {
        cameraFrameLeft = player.getWorldX() - player.getScreenX();
        cameraFrameRight = player.getWorldX() + player.getScreenX();
        cameraFrameTop = player.getWorldY() - player.getScreenY();
        cameraFrameBottom = player.getWorldY() + player.getScreenY();
    }

    public boolean isInCameraFrame(int worldX, int worldY) {
        getCameraFrame();
        return (worldX + GamePanel.TILE_SIZE * 4) > cameraFrameLeft &&
                (worldX - GamePanel.TILE_SIZE * 4) < cameraFrameRight &&
                (worldY + GamePanel.TILE_SIZE * 4) > cameraFrameTop &&
                (worldY - GamePanel.TILE_SIZE * 4) < cameraFrameBottom;
    }

    public int convertToScreenX(int worldX) {
        return worldX - player.getWorldX() + player.getScreenX();
    }

    public int convertToScreenY(int worldY) {
        return worldY - player.getWorldY() + player.getScreenY();
    }
}
