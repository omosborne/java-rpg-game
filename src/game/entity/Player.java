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

    private final int width = 32;
    private final int height = 32;

    public Player (GamePanel gp, KeyInputHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        int hitboxBuffer = width/8;
        int hitboxOverlap = (height-hitboxBuffer)/3;
        hitbox = new Rectangle(hitboxBuffer, hitboxBuffer + hitboxOverlap, width-hitboxBuffer, (height-hitboxBuffer)-hitboxOverlap);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        setDefaultVariables();
        getPlayerImage();
    }

    public void setDefaultVariables () {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = gp.worldWidth/300;
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
            if (keyH.upPressed) direction = 0;
            else if (keyH.leftPressed) direction = 1;
            else if (keyH.downPressed) direction = 2;
            else direction = 3;

            hasCollided = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this);

            if (!hasCollided) {
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

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (byte) (spriteNumber == 1 ? 2 : 1);
                spriteCounter = 0;
            }
        }
    }

    public void draw (Graphics2D g2) {
        BufferedImage sprite = null;

        if (!(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) || hasCollided) {
            switch (direction) {
                case 0 -> sprite = idle_up;
                case 1 -> sprite = idle_left;
                case 2 -> sprite = idle_down;
                case 3 -> sprite = idle_right;
            }
        }
        else switch (direction) {
            case 0 -> sprite = spriteNumber == 1 ? walk_up1 : walk_up2;
            case 1 -> sprite = spriteNumber == 1 ? walk_left1 : walk_left2;
            case 2 -> sprite = spriteNumber == 1 ? walk_down1 : walk_down2;
            case 3 -> sprite = spriteNumber == 1 ? walk_right1 : walk_right2;
        }

        g2.drawImage(sprite, screenX, screenY, width, height, null);
    }
}