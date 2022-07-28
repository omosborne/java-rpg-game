package game.entity;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class HoodedMan extends Entity{
    public HoodedMan(GamePanel gp) {
        super(gp);

        setDirection(Direction.DOWN);
        speed = 1;
        loadSprites();
    }

    private void loadSprites() {
        try {
            idleUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_idle.png")));
            idleDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_idle.png")));
            idleLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_idle.png")));
            idleRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_idle.png")));

            walkUpFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_1.png")));
            walkDownFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_1.png")));
            walkLeftFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_1.png")));
            walkRightFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_1.png")));

            walkUpFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/up_2.png")));
            walkDownFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/down_2.png")));
            walkLeftFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/left_2.png")));
            walkRightFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/hooded-man/right_2.png")));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void setAction() {
        actionCounter++;

        if (actionCounter == 120) {
            Random random = new Random();
            int directionChance = random.nextInt(100) + 1;

            if (directionChance < 25) {
                setDirection(Direction.UP);
            }
            else if (directionChance <= 50) {
                setDirection(Direction.LEFT);
            }
            else if (directionChance <= 75) {
                setDirection(Direction.DOWN);
            }
            else {
                setDirection(Direction.RIGHT);
            }

            actionCounter = 0;
        }
    }
}
