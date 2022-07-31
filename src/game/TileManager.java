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
    private final Camera camera;

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
        camera = gp.getCamera();

        tileTypes = new Tile[26];
        mapTiles = new int[GamePanel.WORLD_MAX_COL][GamePanel.WORLD_MAX_ROW];

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

            while (col < GamePanel.WORLD_MAX_COL && row < GamePanel.WORLD_MAX_ROW) {
                String mapRow = mapReader.readLine();

                while (col < GamePanel.WORLD_MAX_COL) {
                    String[] mapTilesRow = mapRow.split(" ");

                    int mapTile = Integer.parseInt(mapTilesRow[col]);

                    mapTiles[col][row] = mapTile;
                    col++;
                }
                if (col == GamePanel.WORLD_MAX_COL) {
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

        while (worldCol < GamePanel.WORLD_MAX_COL && worldRow < GamePanel.WORLD_MAX_ROW) {
            int mapTile = mapTiles[worldCol][worldRow];

            int worldX = worldCol * GamePanel.TILE_SIZE;
            int worldY = worldRow * GamePanel.TILE_SIZE;
            int screenX = camera.convertToScreenX(worldX);
            int screenY = camera.convertToScreenY(worldY);

            if (camera.isInCameraFrame(worldX, worldY)) {
                g2.drawImage(tileTypes[mapTile].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }

            worldCol++;

            if (worldCol == GamePanel.WORLD_MAX_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
