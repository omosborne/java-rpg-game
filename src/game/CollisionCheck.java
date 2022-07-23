package game;

import game.entity.Entity;
import game.object.SuperObject;

public class CollisionCheck {
    GamePanel gp;

    public CollisionCheck (GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/ gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum, tileNum2;

        switch (entity.direction) {
            // Up
            case 0 -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.hasCollided = true;
            }
            // Left
            case 1 -> {
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.hasCollided = true;
            }
            // Down
            case 2 -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.hasCollided = true;
            }
            // Up
            case 3 -> {
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) entity.hasCollided = true;
            }
        }
    }

    public void checkObject(Entity entity) {

        for (int i = 0; i < gp.obj.length; i++) {
            SuperObject object = gp.obj[i];

            if (object != null) {
               entity.hitbox.x = entity.worldX + entity.hitbox.x;
               entity.hitbox.y = entity.worldY + entity.hitbox.y;

                object.hitbox.x = object.worldX + object.hitbox.x;
                object.hitbox.y = object.worldY + object.hitbox.y;

               switch (entity.direction) {
                   case 0 -> {
                       entity.hitbox.y -= entity.speed;
                       if (entity.hitbox.intersects(object.hitbox)) {
                           if (object.isCollidable) {
                               entity.hasCollided = true;
                           }
                       }
                   }
                   case 1 -> {
                       entity.hitbox.x -= entity.speed;
                       if (entity.hitbox.intersects(object.hitbox)) {
                           if (object.isCollidable) {
                               entity.hasCollided = true;
                           }
                       }
                   }
                   case 2 -> {
                       entity.hitbox.y += entity.speed;
                       if (entity.hitbox.intersects(object.hitbox)) {
                           if (object.isCollidable) {
                               entity.hasCollided = true;
                           }
                       }
                   }
                   case 3 -> {
                       entity.hitbox.x += entity.speed;
                       if (entity.hitbox.intersects(object.hitbox)) {
                           if (object.isCollidable) {
                               entity.hasCollided = true;
                           }
                       }
                   }
               }
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                object.hitbox.x = object.hitboxDefaultX;
                object.hitbox.y = object.hitboxDefaultY;
            }
        }
    }
}
