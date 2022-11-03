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

    protected final int walkSpriteFrames = 4;

    protected BufferedImage idleUp;
    protected BufferedImage idleLeft;
    protected BufferedImage idleDown;
    protected BufferedImage idleRight;

    protected BufferedImage[] walkUpFrame;
    protected BufferedImage[] walkLeftFrame;
    protected BufferedImage[] walkDownFrame;
    protected BufferedImage[] walkRightFrame;

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
    protected int spriteNumber = 0;

    protected Direction direction;

    protected final Rectangle hitbox = new Rectangle(0, 0, 10*GamePanel.ZOOM_FACTOR, 8*GamePanel.ZOOM_FACTOR);
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
        hitbox.setLocation(worldX + (3*GamePanel.ZOOM_FACTOR), worldY + (8*GamePanel.ZOOM_FACTOR));
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
            case UP -> walkUpFrame[spriteNumber];
            case LEFT -> walkLeftFrame[spriteNumber];
            case DOWN -> walkDownFrame[spriteNumber];
            case RIGHT -> walkRightFrame[spriteNumber];
        };
    }

    public void setAction() {
        // Override this.
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gp.setGameState(GamePanel.State.PLAY);
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

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (spriteNumber + 1) % walkSpriteFrames;
                spriteCounter = 0;
            }
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
            g2.drawImage(currentSprite, screenX, screenY, 16*GamePanel.ZOOM_FACTOR, 16*GamePanel.ZOOM_FACTOR, null);

            if (GamePanel.IS_IN_DEBUG_MODE) {
                g2.setColor(Color.red);
                g2.drawRect(screenX + (3*GamePanel.ZOOM_FACTOR), screenY + (8*GamePanel.ZOOM_FACTOR), hitbox.width, hitbox.height);
            }
        }
    }
}
