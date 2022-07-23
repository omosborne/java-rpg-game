package game.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Door extends SuperObject {
    public Door() {
        name = "Door";
        isCollidable = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/game/images/objects/door.png"));
            width = image.getWidth();
            height = image.getHeight();
            hitbox.width = width;
            hitbox.height = height;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
