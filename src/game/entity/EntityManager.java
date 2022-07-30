package game.entity;

import game.GamePanel;

public class EntityManager {
    private final GamePanel gp;

    public EntityManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeEntities() {
        gp.npc[0] = new HoodedMan(gp);
        gp.npc[0].setLocation(21 * gp.tileSize, 21 * gp.tileSize);
    }
}
