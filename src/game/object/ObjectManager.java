package game.object;

import game.GamePanel;

public class ObjectManager {

    private final GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeObjects() {
        SuperObject[] objects = gp.getGameObjects();

        objects[0] = new Box(gp);
        objects[0].setLocation(23 * GamePanel.TILE_SIZE, 7 * GamePanel.TILE_SIZE);

        objects[1] = new Door(gp);
        objects[1].setLocation(23 * GamePanel.TILE_SIZE, 40 * GamePanel.TILE_SIZE);
    }
}
