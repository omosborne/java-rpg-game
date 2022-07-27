package game.object;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean isCollidable = false;
    public int worldX = 0;
    public int worldY = 0;
    public int width = 0;
    public int height = 0;
    public Rectangle hitbox = new Rectangle(0,0,32,32);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (isInCameraFrame(gp)) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame(GamePanel gp) {
        return (worldX + gp.tileSize * 2) > gp.player.worldX - gp.player.screenX &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.worldX + gp.player.screenX &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.worldY - gp.player.screenY &&        // Upper Screen.
                (worldY - gp.tileSize * 2) < gp.player.worldY + gp.player.screenY;          // Bottom Screen
    }
}
