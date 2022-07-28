package game.entity;

import game.GamePanel;
import game.KeyInputHandler;

import javax.imageio.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    KeyInputHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gp, KeyInputHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultVariables();
        loadSprites();
    }

    public void setDefaultVariables () {
        setDirection(Direction.DOWN);
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 3;
    }

    public void loadSprites() {
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
        if (noDirectionKeysPressed()) return;

        if (keyH.upPressed) {
            setDirection(Direction.UP);
        }
        else if (keyH.leftPressed) {
            setDirection(Direction.LEFT);
        }
        else if (keyH.downPressed) {
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
        if (keyH.upPressed  && keyH.leftPressed) {
            worldY -= speed;
            worldX -= speed;
        }
        else if (keyH.leftPressed  && keyH.downPressed) {
            worldX -= speed;
            worldY += speed;
        }
        else if (keyH.downPressed  && keyH.rightPressed) {
            worldY += speed;
            worldX += speed;
        }
        else if (keyH.rightPressed  && keyH.upPressed) {
            worldX += speed;
            worldY -= speed;
        }
        else if (keyH.upPressed) {
            worldY -= speed;
        }
        else if (keyH.leftPressed) {
            worldX -= speed;
        }
        else if (keyH.downPressed) {
            worldY += speed;
        }
        else if (keyH.rightPressed) {
            worldX += speed;
        }
    }

    @Override
    public void draw (Graphics2D g2) {

        if (noDirectionKeysPressed()) {
            currentSprite = getIdleSprite();
        }
        else {
            currentSprite = getWalkSprite();
        }

        g2.drawImage(currentSprite, screenX, screenY, width, height, null);
    }

    private boolean noDirectionKeysPressed() {
        return !(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed);
    }
}