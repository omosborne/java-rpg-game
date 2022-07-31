package game.entity;

import game.GamePanel;

public class EntityManager {
    private final GamePanel gp;

    public EntityManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeEntities() {
        Entity[] entities = gp.getGameEntities();

        entities[0] = new HoodedMan(gp);
        entities[0].setLocation(21 * GamePanel.TILE_SIZE, 21 * GamePanel.TILE_SIZE);
    }
}
