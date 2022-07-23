package game.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Box extends SuperObject {
    public Box() {
        name = "Box";
        isCollidable = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/game/images/objects/box.png"));
            width = image.getWidth();
            height = image.getHeight();
            hitbox.width = width;
            hitbox.height = height;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}