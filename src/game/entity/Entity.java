package game.entity;

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

    GamePanel gp;

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

    private Direction direction;

    public Rectangle hitbox = new Rectangle(8, 16, 16, 16);
    public int hitboxDefaultX;
    public int hitboxDefaultY;
    private boolean collided = false;

    protected int actionCounter = 0;

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

    public Entity(GamePanel gp) {
        this.gp = gp;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
    }

    public BufferedImage getIdleSprite() {
        return switch (direction) {
            case UP -> idleUp;
            case LEFT -> idleLeft;
            case DOWN -> idleDown;
            case RIGHT -> idleRight;
        };
    }

    public BufferedImage getWalkSprite() {
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
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this);
        gp.cChecker.checkPlayer(this);

        if (!hasCollided()) {
            switch (direction) {
                case UP -> worldY -= speed;
                case LEFT -> worldX -= speed;
                case DOWN -> worldY += speed;
                case RIGHT -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = (byte) (spriteNumber == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (isInCameraFrame()) {
            currentSprite = getWalkSprite();
            g2.drawImage(currentSprite, screenX, screenY, width, height, null);
        }
    }

    private boolean isInCameraFrame() {
        return (worldX + gp.tileSize * 2) > gp.player.worldX - gp.player.screenX &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.worldX + gp.player.screenX &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.worldY - gp.player.screenY &&        // Top Screen.
                (worldY - gp.tileSize * 2) < gp.player.worldY + gp.player.screenY;          // Bottom Screen.
    }
}
