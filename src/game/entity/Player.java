package game.entity;

import game.GamePanel;
import game.KeyInputHandler;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    private final KeyInputHandler keys;

    private final int screenX;
    private final int screenY;

    private final int walkSpeed = 3;
    private final int runSpeed = 8;

    private final int walkFrames = 1;
    private final int runFrames = 2;

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public boolean isRunning() {
        return keys.isShiftPressed() && !keys.noDirectionKeysPressed();
    }

    public Player(GamePanel gp, KeyInputHandler keyHandler) {
        super(gp);

        this.keys = keyHandler;

        screenX = (GamePanel.SCREEN_MIN_WIDTH) / 2 - (GamePanel.TILE_SIZE / 2);
        screenY = (GamePanel.SCREEN_MIN_HEIGHT) / 2 - (GamePanel.TILE_SIZE / 2);

        walkUpFrame = new BufferedImage[walkSpriteFrames];
        walkLeftFrame = new BufferedImage[walkSpriteFrames];
        walkDownFrame = new BufferedImage[walkSpriteFrames];
        walkRightFrame = new BufferedImage[walkSpriteFrames];

        setDefaultVariables();
        loadSprites();
    }

    private void setDefaultVariables() {
        setDirection(Direction.DOWN);
        worldX = GamePanel.TILE_SIZE * 5;
        worldY = GamePanel.TILE_SIZE * 5;
        speed = walkSpeed;
        updateHitbox();
    }

    private void loadSprites() {
        try {
            idleUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_idle.png")));
            idleLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_idle.png")));
            idleDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_idle.png")));
            idleRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_idle.png")));

            walkUpFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_1.png")));
            walkUpFrame[1] = idleUp;
            walkUpFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_2.png")));
            walkUpFrame[3] = idleUp;

            walkLeftFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_1.png")));
            walkLeftFrame[1] = idleLeft;
            walkLeftFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_2.png")));
            walkLeftFrame[3] = idleLeft;

            walkDownFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_1.png")));
            walkDownFrame[1] = idleDown;
            walkDownFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_2.png")));
            walkDownFrame[3] = idleDown;

            walkRightFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_1.png")));
            walkRightFrame[1] = idleRight;
            walkRightFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_2.png")));
            walkRightFrame[3] = idleRight;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (keys.noDirectionKeysPressed()) return;

        if (keys.isUpPressed()) {
            setDirection(Direction.UP);
        }
        else if (keys.isLeftPressed()) {
            setDirection(Direction.LEFT);
        }
        else if (keys.isDownPressed()) {
            setDirection(Direction.DOWN);
        }
        else {
            setDirection(Direction.RIGHT);
        }

        collisionOccurred(false);
        gp.getCollisionHandler().checkTile(this);
        gp.getCollisionHandler().checkObject(this);
        gp.getCollisionHandler().checkEntity(this, gp.getGameEntities());

        if (!hasCollided()) {
            updatePlayerPosition();
        }

        int nextFrame = walkFrames;

        if (isRunning()) {
            nextFrame = runFrames;
            if (spriteNumber == 1) spriteNumber = 0;
        }
        spriteCounter++;

        if (spriteCounter > 10) {
            spriteNumber = (spriteNumber + nextFrame) % walkSpriteFrames;
            spriteCounter = 0;
        }
    }

    private void updatePlayerPosition() {
        speed = isRunning() ? runSpeed : walkSpeed;

        if (keys.isUpLeftPressed()) {
            worldY -= speed;
            worldX -= speed;
        }
        else if (keys.isLeftDownPressed()) {
            worldX -= speed;
            worldY += speed;
        }
        else if (keys.isDownRightPressed()) {
            worldY += speed;
            worldX += speed;
        }
        else if (keys.isRightUpPressed()) {
            worldX += speed;
            worldY -= speed;
        }
        else if (keys.isUpPressed()) {
            worldY -= speed;
        }
        else if (keys.isLeftPressed()) {
            worldX -= speed;
        }
        else if (keys.isDownPressed()) {
            worldY += speed;
        }
        else if (keys.isRightPressed()) {
            worldX += speed;
        }
        updateHitbox();
    }

    @Override
    public void draw(Graphics2D g2) {

        if (keys.noDirectionKeysPressed()) {
            currentSprite = getIdleSprite();
        }
        else {
            currentSprite = getWalkSprite();
        }

        g2.drawImage(currentSprite, screenX, screenY, 16*GamePanel.ZOOM_FACTOR, 16*GamePanel.ZOOM_FACTOR, null);

        if (GamePanel.IS_IN_DEBUG_MODE) {
            g2.setColor(Color.red);
            g2.drawRect(screenX + (3*GamePanel.ZOOM_FACTOR), screenY + (8*GamePanel.ZOOM_FACTOR), hitbox.width, hitbox.height);
        }
    }
}