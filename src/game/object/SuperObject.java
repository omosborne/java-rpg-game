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
    protected final Rectangle hitbox = new Rectangle(0, 0, 32, 32);

    public void setLocation(int x, int y) {
        worldX = x;
        worldY = y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    protected void canCollide(boolean collidable) {
        isCollidable = collidable;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (isInCameraFrame(gp)) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame(GamePanel gp) {
        return (worldX + gp.tileSize * 2) > gp.player.getWorldX() - gp.player.getScreenX() &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.getWorldX() + gp.player.getScreenX() &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.getWorldY() - gp.player.getScreenY() &&        // Upper Screen.
                (worldY - gp.tileSize * 2) < gp.player.getWorldY() + gp.player.getScreenY();          // Bottom Screen
    }
}
