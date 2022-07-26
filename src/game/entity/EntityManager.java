package game.entity;

import game.GamePanel;

public class EntityManager {
    GamePanel gp;

    public EntityManager(GamePanel gp) {
        this.gp = gp;
    }

    public void placeEntities() {
        gp.npc[0] = new HoodedMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
}
