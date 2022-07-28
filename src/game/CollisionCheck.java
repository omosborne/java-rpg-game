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
        int entityLeftWorldX = entity.getWorldX() + entity.hitbox.x;
        int entityRightWorldX = entity.getWorldX() + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.getWorldY() + entity.hitbox.y;
        int entityBottomWorldY = entity.getWorldY() + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum = 0;
        int tileNum2 = 0;

        switch (entity.getDirection()) {
            case UP -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed())/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.collisionOccurred(true);
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOccurred(true);
                }
            }
            case LEFT -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.collisionOccurred(true);
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOccurred(true);
                }
            }
            case DOWN -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.collisionOccurred(true);
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOccurred(true);
                }
            }
            case RIGHT -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed())/gp.tileSize;

                try {
                    tileNum = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                } catch (IndexOutOfBoundsException e) {
                    entity.collisionOccurred(true);
                    break;
                }

                if (gp.tileM.tile[tileNum].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOccurred(true);
                }
            }
        }
    }

    public void checkObject(Entity entity) {

        for (int i = 0; i < gp.obj.length; i++) {
            SuperObject object = gp.obj[i];

            if (object == null) continue;

            entity.hitbox.x += entity.getWorldX();
            entity.hitbox.y += entity.getWorldY();

            object.hitbox.x += object.worldX;
            object.hitbox.y += object.worldY;

            switch (entity.getDirection()) {
                case UP -> entity.hitbox.y -= entity.getSpeed();
                case LEFT -> entity.hitbox.x -= entity.getSpeed();
                case DOWN -> entity.hitbox.y += entity.getSpeed();
                case RIGHT -> entity.hitbox.x += entity.getSpeed();
            }

            if (entity.hitbox.intersects(object.hitbox) && object.isCollidable) {
                entity.collisionOccurred(true);
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

            entity.hitbox.x += entity.getWorldX();
            entity.hitbox.y += entity.getWorldY();

            currentTarget.hitbox.x += currentTarget.getWorldX();
            currentTarget.hitbox.y += currentTarget.getWorldY();

            switch (entity.getDirection()) {
                case UP -> entity.hitbox.y -= entity.getSpeed();
                case LEFT -> entity.hitbox.x -= entity.getSpeed();
                case DOWN -> entity.hitbox.y += entity.getSpeed();
                case RIGHT -> entity.hitbox.x += entity.getSpeed();
            }

            if (entity.hitbox.intersects(currentTarget.hitbox)) {
                entity.collisionOccurred(true);
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

        entity.hitbox.x += entity.getWorldX();
        entity.hitbox.y += entity.getWorldY();

        player.hitbox.x += player.getWorldX();
        player.hitbox.y += player.getWorldY();

        switch (entity.getDirection()) {
            case UP -> entity.hitbox.y -= entity.getSpeed();
            case LEFT -> entity.hitbox.x -= entity.getSpeed();
            case DOWN -> entity.hitbox.y += entity.getSpeed();
            case RIGHT -> entity.hitbox.x += entity.getSpeed();
        }

        if (entity.hitbox.intersects(player.hitbox)) {
            entity.collisionOccurred(true);
            gp.ui.displayNotification("Collision detected!");
        }

        // Reset hitboxes.
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        player.hitbox.x = player.hitboxDefaultX;
        player.hitbox.y = player.hitboxDefaultY;
    }
}
