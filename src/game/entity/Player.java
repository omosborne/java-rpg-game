package game.entity;

import game.GamePanel;
import game.KeyInputHandler;

import javax.imageio.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    private final KeyInputHandler keys;

    private final int screenX;
    private final int screenY;

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public Player (GamePanel gp, KeyInputHandler keyHandler) {
        super(gp);

        this.keys = keyHandler;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultVariables();
        loadSprites();
    }

    private void setDefaultVariables () {
        setDirection(Direction.DOWN);
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 3;
        updateHitbox(worldX, worldY);
    }

    private void loadSprites() {
        try {
            idleUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_idle.png")));
            idleDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_idle.png")));
            idleLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_idle.png")));
            idleRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_idle.png")));

            walkUpFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_1.png")));
            walkDownFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_1.png")));
            walkLeftFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_1.png")));
            walkRightFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_1.png")));

            walkUpFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/up_2.png")));
            walkDownFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/down_2.png")));
            walkLeftFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/left_2.png")));
            walkRightFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/character/right_2.png")));
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
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this);
        gp.cChecker.checkEntity(this, gp.npc);

        if (!hasCollided()) {
            updatePlayerPosition();
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = (spriteNumber == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }

    private void updatePlayerPosition() {
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
        updateHitbox(worldX, worldY);
    }

    @Override
    public void draw(Graphics2D g2) {

        if (keys.noDirectionKeysPressed()) {
            currentSprite = getIdleSprite();
        }
        else {
            currentSprite = getWalkSprite();
        }

        g2.drawImage(currentSprite, screenX, screenY, width, height, null);
    }
}