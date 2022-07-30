package game;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private boolean collision = false;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage tileImage) {
        image = tileImage;
    }

    public boolean isCollidable() {
        return collision;
    }

    public void setCollidable(boolean isCollidable) {
        collision = isCollidable;
    }
}
