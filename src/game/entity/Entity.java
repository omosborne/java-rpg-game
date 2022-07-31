package game.entity;

import game.CollisionHandler;
import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT
    }

    protected final GamePanel gp;

    protected int worldX = 0;
    protected int worldY = 0;
    protected int speed = 0;

    protected int width = 32;
    protected int height = 32;

    protected BufferedImage idleUp;
    protected BufferedImage idleLeft;
    protected BufferedImage idleDown;
    protected BufferedImage idleRight;

    protected BufferedImage walkUpFrame1;
    protected BufferedImage walkLeftFrame1;
    protected BufferedImage walkDownFrame1;
    protected BufferedImage walkRightFrame1;

    protected BufferedImage walkUpFrame2;
    protected BufferedImage walkLeftFrame2;
    protected BufferedImage walkDownFrame2;
    protected BufferedImage walkRightFrame2;

    protected BufferedImage currentSprite;
    protected int spriteCounter = 0;
    protected int spriteNumber = 1;

    protected Direction direction;

    protected final Rectangle hitbox = new Rectangle(0, 0, 16, 16);
    private boolean collided = false;
    protected final CollisionHandler collisionHandler;

    protected int actionCounter = 0;

    public void setLocation(int x, int y) {
        worldX = x;
        worldY = y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasCollided() {
        return collided;
    }

    public void collisionOccurred(boolean collisionState) {
        collided = collisionState;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    protected void updateHitbox(int ownerX, int ownerY) {
        hitbox.setLocation(ownerX + 8, ownerY + 16);
    }

    public Entity(GamePanel gp) {
        this.gp = gp;
        this.collisionHandler = gp.getCollisionHandler();

    }

    protected BufferedImage getIdleSprite() {
        return switch (direction) {
            case UP -> idleUp;
            case LEFT -> idleLeft;
            case DOWN -> idleDown;
            case RIGHT -> idleRight;
        };
    }

    protected BufferedImage getWalkSprite() {
        return switch (direction) {
            case UP -> spriteNumber == 1 ? walkUpFrame1 : walkUpFrame2;
            case LEFT -> spriteNumber == 1 ? walkLeftFrame1 : walkLeftFrame2;
            case DOWN -> spriteNumber == 1 ? walkDownFrame1 : walkDownFrame2;
            case RIGHT -> spriteNumber == 1 ? walkRightFrame1 : walkRightFrame2;
        };
    }

    public void setAction() {
        // Override this.
    }

    public void update() {
        setAction();

        collisionOccurred(false);
        collisionHandler.checkTile(this);
        collisionHandler.checkObject(this);
        collisionHandler.checkPlayer(this);

        if (!hasCollided()) {
            updateEntityPosition();
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = spriteNumber == 1 ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void updateEntityPosition() {
        switch (direction) {
            case UP -> worldY -= speed;
            case LEFT -> worldX -= speed;
            case DOWN -> worldY += speed;
            case RIGHT -> worldX += speed;
        }
        updateHitbox(worldX, worldY);
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.getScreenX();
        int screenY = worldY - gp.player.worldY + gp.player.getScreenY();

        if (isInCameraFrame()) {
            currentSprite = getWalkSprite();
            g2.drawImage(currentSprite, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame() {
        return (worldX + GamePanel.TILE_SIZE * 2) > gp.player.worldX - gp.player.getScreenX() &&         // Left Screen.
                (worldX - GamePanel.TILE_SIZE * 2) < gp.player.worldX + gp.player.getScreenX() &&        // Right Screen.
                (worldY + GamePanel.TILE_SIZE * 2) > gp.player.worldY - gp.player.getScreenY() &&        // Top Screen.
                (worldY - GamePanel.TILE_SIZE * 2) < gp.player.worldY + gp.player.getScreenY();          // Bottom Screen.
    }
}
