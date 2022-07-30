package game;

import game.entity.Entity;
import game.entity.Player;
import game.object.SuperObject;

import java.awt.*;

public class CollisionCheck {
    private final GamePanel gp;

    public CollisionCheck (GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        Rectangle hitbox = entity.getHitbox();

        int leftCol = hitbox.x;
        int rightCol = hitbox.x + hitbox.width;
        int topRow = hitbox.y;
        int bottomRow = hitbox.y + hitbox.height;

        boolean collidedWithTile = false;

        switch (entity.getDirection()) {
            case UP -> {
                topRow -= entity.getSpeed();
                collidedWithTile = testTileCollision(leftCol, topRow, rightCol, topRow);
            }
            case LEFT -> {
                leftCol -= entity.getSpeed();
                collidedWithTile = testTileCollision(leftCol, topRow, leftCol, bottomRow);
            }
            case DOWN -> {
                bottomRow += entity.getSpeed();
                collidedWithTile = testTileCollision(leftCol, bottomRow, rightCol, bottomRow);
            }
            case RIGHT -> {
                rightCol += entity.getSpeed();
                collidedWithTile = testTileCollision(rightCol, topRow, rightCol, bottomRow);
            }
        }
        if (collidedWithTile) entity.collisionOccurred(true);
    }

    private boolean testTileCollision(int tile1Col, int tile1Row, int tile2Col, int tile2Row) {
        int mapTileCheck1;
        int mapTileCheck2;

        try {
            mapTileCheck1 = gp.tileM.getMapTile(tile1Col / gp.tileSize, tile1Row / gp.tileSize);
            mapTileCheck2 = gp.tileM.getMapTile(tile2Col / gp.tileSize, tile2Row / gp.tileSize);
        } catch (IndexOutOfBoundsException e) {
            return true;
        }

        return gp.tileM.getTileType(mapTileCheck1).isCollidable() || gp.tileM.getTileType(mapTileCheck2).isCollidable();
    }

    public void checkObject(Entity entity) {
        for (SuperObject object : gp.obj) {
            if (object == null || !object.isCollidable()) continue;
            checkCollision(entity, object.getHitbox());
        }
    }

    public void checkEntity(Entity entity, Entity[] entities) {
        for (Entity target : entities) {
            if (target == null) continue;
            checkCollision(entity, target.getHitbox());
        }
    }

    public void checkPlayer(Entity entity) {
        Player player = gp.player;
        checkCollision(entity, player.getHitbox());
    }

    private void checkCollision(Entity entity, Rectangle targetHitbox) {
        int oldHitboxX = entity.getHitbox().x;
        int oldHitboxY = entity.getHitbox().y;

        moveEntityHitbox(entity);

        if (entity.getHitbox().intersects(targetHitbox)) {
            entity.collisionOccurred(true);
            gp.ui.displayNotification("Collision detected!");
        }

        entity.getHitbox().x = oldHitboxX;
        entity.getHitbox().y = oldHitboxY;
    }

    private void moveEntityHitbox(Entity entity) {
        switch (entity.getDirection()) {
            case UP -> entity.getHitbox().y -= entity.getSpeed();
            case LEFT -> entity.getHitbox().x -= entity.getSpeed();
            case DOWN -> entity.getHitbox().y += entity.getSpeed();
            case RIGHT -> entity.getHitbox().x += entity.getSpeed();
        }
    }
}
