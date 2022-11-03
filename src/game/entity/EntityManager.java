package game.entity;

import game.GamePanel;

public class EntityManager {
    private final GamePanel gp;

    public EntityManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeEntities() {
        Entity[] entities = gp.getGameEntities();

        entities[0] = new Monk(gp);
        entities[0].setLocation(11 * GamePanel.TILE_SIZE, 11 * GamePanel.TILE_SIZE);
    }
}
