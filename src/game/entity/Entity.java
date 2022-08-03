package game.entity;

import game.Camera;
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
    protected final Camera camera;

    protected int worldX = 0;
    protected int worldY = 0;
    protected int speed = 0;

    protected int width = 44;
    protected int height = 58;

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

    protected final Rectangle hitbox = new Rectangle(0, 0, 32, 20);
    private boolean collided = false;

    protected int actionCounter = 0;

    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;

    public void setLocation(int x, int y) {
        worldX = x;
        worldY = y;
        updateHitbox();
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

    protected void updateHitbox() {
        hitbox.setLocation(worldX + 6, worldY + 36);
    }

    public Entity(GamePanel gp) {
        this.gp = gp;
        camera = gp.getCamera();
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

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.getGameUI().currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.getPlayer().getDirection()) {
            case UP -> setDirection(Direction.DOWN);
            case LEFT -> setDirection(Direction.RIGHT);
            case DOWN -> setDirection(Direction.UP);
            case RIGHT -> setDirection(Direction.LEFT);
        }
    }

    public void update() {
        setAction();

        collisionOccurred(false);
        gp.getCollisionHandler().checkTile(this);
        gp.getCollisionHandler().checkObject(this);
        gp.getCollisionHandler().checkPlayer(this);

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
        updateHitbox();
    }

    public void draw(Graphics2D g2) {
        int screenX = camera.convertToScreenX(worldX);
        int screenY = camera.convertToScreenY(worldY);

        if (camera.isInCameraFrame(worldX, worldY)) {
            currentSprite = getWalkSprite();
            g2.drawImage(currentSprite, screenX, screenY, width, height, null);
        }
    }
}
