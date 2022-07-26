package game.entity;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

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
}
