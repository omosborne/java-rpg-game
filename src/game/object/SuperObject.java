package game.object;

import game.Camera;
import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    private final Camera camera;

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

    public SuperObject(GamePanel gp) {
        camera = gp.getCamera();
    }

    public void draw(Graphics2D g2) {
        int screenX = camera.convertToScreenX(worldX);
        int screenY = camera.convertToScreenY(worldY);

        if (camera.isInCameraFrame(worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }
}
