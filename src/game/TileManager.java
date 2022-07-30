package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tileTypes;
    private final int[][] mapTiles;

    public Tile getTileType(int mapTile) {
        return tileTypes[mapTile];
    }

    public int getMapTile(int col, int row) {
        return mapTiles[col][row];
    }

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tileTypes = new Tile[26];
        mapTiles = new int[gp.worldMaxCol][gp.worldMaxRow];

        loadTileImages();
        loadMapTiles("/game/maps/world02.txt");
    }

    private void loadTileImages() {
        try {
            for (int i = 10; i < tileTypes.length; i++) {
                tileTypes[i] = new Tile();
                tileTypes[i].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/tiles/tile" + i + ".png"))));
            }
            tileTypes[10].setCollidable(true);
            for (int i = 19; i < 25; i++) tileTypes[i].setCollidable(true);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void loadMapTiles(String mapFilePath) {
        try (InputStream mapFileInput = Objects.requireNonNull(getClass().getResourceAsStream(mapFilePath));
             BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapFileInput))) {

            int col = 0;
            int row = 0;

            while (col < gp.worldMaxCol && row < gp.worldMaxRow) {
                String mapRow = mapReader.readLine();

                while (col < gp.worldMaxCol) {
                    String[] mapTilesRow = mapRow.split(" ");

                    int mapTile = Integer.parseInt(mapTilesRow[col]);

                    mapTiles[col][row] = mapTile;
                    col++;
                }
                if (col == gp.worldMaxCol) {
                    col = 0;
                    row++;
                }
            }
            } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.worldMaxCol && worldRow < gp.worldMaxRow) {
            int mapTile = mapTiles[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
            int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

            if (isInCameraFrame(worldX, worldY)) {
                g2.drawImage(tileTypes[mapTile].getImage(), screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.worldMaxCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private boolean isInCameraFrame(int worldX, int worldY) {
        return (worldX + gp.tileSize * 2) > gp.player.getWorldX() - gp.player.screenX &&         // Left Screen.
                (worldX - gp.tileSize * 2) < gp.player.getWorldX() + gp.player.screenX &&        // Right Screen.
                (worldY + gp.tileSize * 2) > gp.player.getWorldY() - gp.player.screenY &&        // Top Screen.
                (worldY - gp.tileSize * 2) < gp.player.getWorldY() + gp.player.screenY;          // Bottom Screen.
    }
}
