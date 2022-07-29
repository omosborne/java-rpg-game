package game.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends SuperObject {
    public Door() {
        name = "Door";
        canCollide(true);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/objects/door.png")));
            width = image.getWidth();
            height = image.getHeight();
            hitbox.width = width;
            hitbox.height = height;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
