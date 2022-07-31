package game.object;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Box extends SuperObject {
    public Box(GamePanel gp) {
        super(gp);
        name = "Box";
        canCollide(true);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/objects/box.png")));
            width = image.getWidth();
            height = image.getHeight();
            hitbox.width = width;
            hitbox.height = height;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
