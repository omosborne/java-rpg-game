package game.object;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    protected BufferedImage image;
    protected String name;
    protected int worldX = 0;
    protected int worldY = 0;
    protected int width = 0;
    protected int height = 0;
    protected boolean isCollidable = false;
    public Rectangle hitbox = new Rectangle(0,0,32,32);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public void canCollide(boolean collidable) {
        isCollidable = collidable;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

        if (isInCameraFrame(gp)) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame(GamePanel gp) {
        return (worldX + gp.tileSize * 2) > gp.player.getWorldX() - gp.player.screenX &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.getWorldX() + gp.player.screenX &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.getWorldY() - gp.player.screenY &&        // Upper Screen.
                (worldY - gp.tileSize * 2) < gp.player.getWorldY() + gp.player.screenY;          // Bottom Screen
    }
}
