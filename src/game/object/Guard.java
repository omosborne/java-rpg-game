package game.object;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Guard extends SuperObject {
    public Guard(GamePanel gp) {
        super(gp);
        name = "Guard";
        canCollide(true);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/objects/guard.png")));
            width = image.getWidth();
            height = image.getHeight();
            hitbox.width = width;
            hitbox.height = height;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
