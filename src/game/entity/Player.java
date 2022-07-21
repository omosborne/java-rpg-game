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
    public Player (GamePanel gp, KeyInputHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultVariables();
        getPlayerImage();
    }

    public void setDefaultVariables () {
        x = 100;
        y = 100;
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
        if (keyH.upPressed) {
            dir = 0;
            y -= speed;
        }
        else if (keyH.leftPressed) {
            dir = 1;
            x -= speed;
        }
        else if (keyH.downPressed) {
            dir = 2;
            y += speed;
        }
        else if (keyH.rightPressed) {
            dir = 3;
            x += speed;
        }
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = (byte) (spriteNumber == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }

    public void draw (Graphics2D g2) {
        BufferedImage sprite = null;
        switch (dir) {
            case 0 -> sprite = spriteNumber == 1 ? walk_up1 : walk_up2;
            case 1 -> sprite = spriteNumber == 1 ? walk_left1 : walk_left2;
            case 2 -> sprite = spriteNumber == 1 ? walk_down1 : walk_down2;
            case 3 -> sprite = spriteNumber == 1 ? walk_right1 : walk_right2;
        }
        g2.drawImage(sprite, x, y, gp.tileSize, gp.tileSize, null);
    }
}