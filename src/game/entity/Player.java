package game.entity;

import game.GamePanel;
import game.KeyInputHandler;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyInputHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gp, KeyInputHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        Hitbox = new Rectangle(8, 16, 32, 32);

        setDefaultVariables();
        getPlayerImage();
    }

    public void setDefaultVariables () {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
    }

    public void getPlayerImage () {
        try {
            // Idle Sprites
            idle_up = ImageIO.read(getClass().getResourceAsStream("/game/images/character/up_idle.png"));
            idle_down = ImageIO.read(getClass().getResourceAsStream("/game/images/character/down_idle.png"));
            idle_left = ImageIO.read(getClass().getResourceAsStream("/game/images/character/left_idle.png"));
            idle_right = ImageIO.read(getClass().getResourceAsStream("/game/images/character/right_idle.png"));

            // Walk 1 Sprites
            walk_up1 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/up_1.png"));
            walk_down1 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/down_1.png"));
            walk_left1 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/left_1.png"));
            walk_right1 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/right_1.png"));

            // Walk 2 Sprites
            walk_up2 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/up_2.png"));
            walk_down2 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/down_2.png"));
            walk_left2 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/left_2.png"));
            walk_right2 = ImageIO.read(getClass().getResourceAsStream("/game/images/character/right_2.png"));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void update () {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) dir = 0;
            else if (keyH.leftPressed) dir = 1;
            else if (keyH.downPressed) dir = 2;
            else if (keyH.rightPressed) dir = 3;

            collisionOn = false;
            gp.cChecker.checktile(this);

            if (collisionOn == false) switch (dir) {
                case 0 -> worldY -= speed;
                case 1 -> worldX -= speed;
                case 2 -> worldY += speed;
                case 3 -> worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (byte) (spriteNumber == 1 ? 2 : 1);
                spriteCounter = 0;
            }
        }
    }

    public void draw (Graphics2D g2) {
        BufferedImage sprite = null;
        if (!(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
            switch (dir) {
                case 0 -> sprite = idle_up;
                case 1 -> sprite = idle_left;
                case 2 -> sprite = idle_down;
                case 3 -> sprite = idle_right;
            }
        }
        else switch (dir) {
            case 0 -> sprite = spriteNumber == 1 ? walk_up1 : walk_up2;
            case 1 -> sprite = spriteNumber == 1 ? walk_left1 : walk_left2;
            case 2 -> sprite = spriteNumber == 1 ? walk_down1 : walk_down2;
            case 3 -> sprite = spriteNumber == 1 ? walk_right1 : walk_right2;
        }

        g2.drawImage(sprite, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}