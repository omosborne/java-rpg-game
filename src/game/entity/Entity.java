package game.entity;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;

    public int worldX = 0;
    public int worldY = 0;
    public int speed = 0;

    protected int width = 32;
    protected int height = 32;

    public BufferedImage idle_left, idle_right, idle_up, idle_down;
    public BufferedImage walk_left1, walk_right1, walk_up1, walk_down1;
    public BufferedImage walk_left2, walk_right2, walk_up2, walk_down2;

    public int direction = 0;
    public byte spriteCounter = 0;
    public byte spriteNumber = 1;

    public Rectangle hitbox = new Rectangle(8, 16, 16, 16);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;
    public boolean hasCollided = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {

        BufferedImage sprite = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.player.screenX > gp.player.worldX) {
            screenX = worldX;
        }
        if (gp.player.screenY > gp.player.worldY) {
            screenY = worldY;
        }

        int rightOffset = gp.screenWidth - gp.player.screenX;
        if (rightOffset > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }

        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if (bottomOffset > gp.worldHeight - gp.player.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if (isInCameraFrame()) {

            switch (direction) {
                case 0 -> sprite = spriteNumber == 1 ? walk_up1 : walk_up2;
                case 1 -> sprite = spriteNumber == 1 ? walk_left1 : walk_left2;
                case 2 -> sprite = spriteNumber == 1 ? walk_down1 : walk_down2;
                case 3 -> sprite = spriteNumber == 1 ? walk_right1 : walk_right2;
            }

            g2.drawImage(sprite, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame() {
        return worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
    }
}
