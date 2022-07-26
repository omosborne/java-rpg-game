package game;

import game.entity.Entity;
import game.entity.Player;
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
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum = 0;
        int tileNum2 = 0;

        switch (entity.direction) {
            // Up
            case 0 -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.hasCollided = true;
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.hasCollided = true;
                }
            }
            // Left
            case 1 -> {
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.hasCollided = true;
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.hasCollided = true;
                }
            }
            // Down
            case 2 -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.hasCollided = true;
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.hasCollided = true;
                }
            }
            // Right
            case 3 -> {
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.hasCollided = true;
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.hasCollided = true;
                }
            }
        }
    }

    public void checkObject(Entity entity) {

        for (int i = 0; i < gp.obj.length; i++) {
            SuperObject object = gp.obj[i];

            if (object == null) continue;

            entity.hitbox.x = entity.worldX + entity.hitbox.x;
            entity.hitbox.y = entity.worldY + entity.hitbox.y;

            object.hitbox.x = object.worldX + object.hitbox.x;
            object.hitbox.y = object.worldY + object.hitbox.y;

            switch (entity.direction) {
                case 0 -> entity.hitbox.y -= entity.speed;
                case 1 -> entity.hitbox.x -= entity.speed;
                case 2 -> entity.hitbox.y += entity.speed;
                case 3 -> entity.hitbox.x += entity.speed;
            }

            if (entity.hitbox.intersects(object.hitbox) && object.isCollidable) {
                entity.hasCollided = true;
                gp.ui.displayNotification("Collision detected!");
            }

            // Reset hitboxes.
            entity.hitbox.x = entity.hitboxDefaultX;
            entity.hitbox.y = entity.hitboxDefaultY;
            object.hitbox.x = object.hitboxDefaultX;
            object.hitbox.y = object.hitboxDefaultY;

        }
    }

    public void checkEntity(Entity entity, Entity[] target) {
        for (int i = 0; i < target.length; i++) {
            Entity currentTarget = target[i];

            if (currentTarget == null) continue;

            entity.hitbox.x = entity.worldX + entity.hitbox.x;
            entity.hitbox.y = entity.worldY + entity.hitbox.y;

            currentTarget.hitbox.x = currentTarget.worldX + currentTarget.hitbox.x;
            currentTarget.hitbox.y = currentTarget.worldY + currentTarget.hitbox.y;

            switch (entity.direction) {
                case 0 -> entity.hitbox.y -= entity.speed;
                case 1 -> entity.hitbox.x -= entity.speed;
                case 2 -> entity.hitbox.y += entity.speed;
                case 3 -> entity.hitbox.x += entity.speed;
            }

            if (entity.hitbox.intersects(currentTarget.hitbox)) {
                entity.hasCollided = true;
                gp.ui.displayNotification("Collision detected!");
            }

            // Reset hitboxes.
            entity.hitbox.x = entity.hitboxDefaultX;
            entity.hitbox.y = entity.hitboxDefaultY;
            currentTarget.hitbox.x = currentTarget.hitboxDefaultX;
            currentTarget.hitbox.y = currentTarget.hitboxDefaultY;

        }
    }

    public void checkPlayer(Entity entity) {
        Player player = gp.player;

        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;

        player.hitbox.x = player.worldX + player.hitbox.x;
        player.hitbox.y = player.worldY + player.hitbox.y;

        switch (entity.direction) {
            case 0 -> entity.hitbox.y -= entity.speed;
            case 1 -> entity.hitbox.x -= entity.speed;
            case 2 -> entity.hitbox.y += entity.speed;
            case 3 -> entity.hitbox.x += entity.speed;
        }

        if (entity.hitbox.intersects(player.hitbox)) {
            entity.hasCollided = true;
            gp.ui.displayNotification("Collision detected!");
        }

        // Reset hitboxes.
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        player.hitbox.x = player.hitboxDefaultX;
        player.hitbox.y = player.hitboxDefaultY;
    }
}
