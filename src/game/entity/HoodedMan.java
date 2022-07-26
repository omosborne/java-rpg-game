package game.entity;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class HoodedMan extends Entity{
    public HoodedMan(GamePanel gp) {
        super(gp);

        direction = 2;
        speed = 1;
        getImages();
    }

    private void getImages () {
        try {
            // Idle Sprites
            idle_up = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_idle.png"));
            idle_down = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_idle.png"));
            idle_left = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_idle.png"));
            idle_right = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_idle.png"));

            // Walk 1 Sprites
            walk_up1 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_1.png"));
            walk_down1 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_1.png"));
            walk_left1 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_1.png"));
            walk_right1 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_1.png"));

            // Walk 2 Sprites
            walk_up2 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_2.png"));
            walk_down2 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_2.png"));
            walk_left2 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_2.png"));
            walk_right2 = ImageIO.read(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_2.png"));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void setAction() {
        actionCounter++;

        if (actionCounter == 120) {
            Random random = new Random();
            int directionChance = random.nextInt(100) + 1;

            if (directionChance < 25) direction = 0;
            else if (directionChance > 25 && directionChance <= 50) direction = 1;
            else if (directionChance > 50 && directionChance <= 75) direction = 2;
            else if (directionChance > 75) direction = 3;

            actionCounter = 0;
        }
    }
}
