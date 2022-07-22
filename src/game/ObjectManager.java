package game;

import game.object.Box;

public class ObjectManager {

    GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeObject() {
        gp.obj[0] = new Box();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new Box();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 40 * gp.tileSize;
    }
}
