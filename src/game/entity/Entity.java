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

    public int actionCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
    }

    public void setAction() {
        // Override this.
    }

    public void update() {
        setAction();

        hasCollided = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this);
        gp.cChecker.checkPlayer(this);

        if (!hasCollided) {
            switch (direction) {
                case 0 -> worldY -= speed;
                case 1 -> worldX -= speed;
                case 2 -> worldY += speed;
                case 3 -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = (byte) (spriteNumber == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage sprite = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (isInCameraFrame()) {

            switch (direction) {
                case 0 -> sprite = spriteNumber == 1 ? walk_up1 : walk_up2;
                case 1 -> sprite = spriteNumber == 1 ? walk_left1 : walk_left2;
                case 2 -> sprite = spriteNumber == 1 ? walk_down1 : walk_down2;
                case 3 -> sprite = spriteNumber == 1 ? walk_right1 : walk_right2;
            }
            System.out.println("Draw NPC");
            g2.drawImage(sprite, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame() {
        return (worldX + gp.tileSize * 2) > gp.player.worldX - gp.player.screenX &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.worldX + gp.player.screenX &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.worldY - gp.player.screenY &&        // Upper Screen.
                (worldY - gp.tileSize * 2) < gp.player.worldY + gp.player.screenY;          // Bottom Screen.
    }
}
