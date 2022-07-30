package game.object;

import game.GamePanel;

public class ObjectManager {

    private final GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeObjects() {
        gp.obj[0] = new Box();
        gp.obj[0].setLocation(23 * gp.tileSize, 7 * gp.tileSize);

        gp.obj[1] = new Door();
        gp.obj[1].setLocation(23 * gp.tileSize, 40 * gp.tileSize);
    }
}
