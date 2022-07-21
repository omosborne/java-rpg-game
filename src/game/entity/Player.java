package game.entity;

import game.GamePanel;
import game.KeyInputHandler;

import javax.imageio.*;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyInputHandler keyH;
    public Player (GamePanel gp, KeyInputHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultVariables();
    }

    public void setDefaultVariables () {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void getPlayerImage () {
        try {
            idle_up = ImageIO.read(getClass().getResourceAsStream("../images/idle_up.png"));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void update () {
        if (keyH.upPressed) y -= speed;
        else if (keyH.leftPressed) x -= speed;
        else if (keyH.downPressed) y += speed;
        else if (keyH.rightPressed) x += speed;
    }

    public void draw (Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}