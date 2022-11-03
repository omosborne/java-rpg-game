package game.entity;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Monk extends Entity{
    public Monk(GamePanel gp) {
        super(gp);

        walkUpFrame = new BufferedImage[walkSpriteFrames];
        walkLeftFrame = new BufferedImage[walkSpriteFrames];
        walkDownFrame = new BufferedImage[walkSpriteFrames];
        walkRightFrame = new BufferedImage[walkSpriteFrames];

        setDirection(Direction.DOWN);
        speed = 1;
        loadSprites();
        setDialogue();
    }

    private void loadSprites() {
        try {
            idleUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_up_idle.png")));
            idleLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_left_idle.png")));
            idleDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_down_idle.png")));
            idleRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_right_idle.png")));

            walkUpFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_up_1.png")));
            walkUpFrame[1] = idleUp;
            walkUpFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_up_2.png")));
            walkUpFrame[3] = idleUp;

            walkLeftFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_left_1.png")));
            walkLeftFrame[1] = idleLeft;
            walkLeftFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_left_2.png")));
            walkLeftFrame[3] = idleLeft;

            walkDownFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_down_1.png")));
            walkDownFrame[1] = idleDown;
            walkDownFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_down_2.png")));
            walkDownFrame[3] = idleDown;

            walkRightFrame[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_right_1.png")));
            walkRightFrame[1] = idleRight;
            walkRightFrame[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/npcs/monk/monk_right_2.png")));
            walkRightFrame[3] = idleRight;
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void setDialogue() {
        dialogues[0] = "Hello, Player!";
        dialogues[1] = "Welcome to the world. I am here to test functionality. What a fun life! Welcome to the world. I am\nhere to test functionality. What a fun life! Welcome to the world. I am here to test functionality.\nWhat a fun life!";
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
