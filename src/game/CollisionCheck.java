package game;

import game.entity.Entity;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;
    }
    public void checktile (Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.Hitbox.x;
        int entityRightWorldX = entity.worldX + entity.Hitbox.x + entity.Hitbox.width;
        int entityTopWorldY = entity.worldY + entity.Hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.Hitbox.y + entity.Hitbox.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/ gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum, tileNum2;

        switch (entity.dir) {
            case 0 -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.collisionOn = true;
            }
            case 1 -> {
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.collisionOn = true;
            }
            case 2 -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.collisionOn = true;
            }
            case 3 -> {
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.collisionOn = true;
            }
        }
    }
}
