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
    private String[][][] mapTiles;
    private int mapLayers;

    private String map = GamePanel.DEFAULT_MAP;

    public String getMap() {
        return map;
    }

    public void setMap(String newMap) {
        map = newMap;
        loadMap();
    }

    public Tile getTileType(int mapTile) {
        return tileTypes[mapTile];
    }

    public String getMapTile(int layer, int row, int col) {
        return mapTiles[layer][row][col];
    }

    public TileManager(GamePanel gp) {
        this.gp = gp;
        camera = gp.getCamera();

        tileTypes = new Tile[28];

        loadTileImages();
        loadMap();
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

    private void loadMap() {
        String mapFilePath = "/game/maps/" + map + ".txt";
        try (InputStream mapFileInput = Objects.requireNonNull(getClass().getResourceAsStream(mapFilePath));
             BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapFileInput))) {
            mapLayers = Integer.parseInt(mapReader.readLine());
            mapTiles = new String[mapLayers][GamePanel.WORLD_MAX_ROW][GamePanel.WORLD_MAX_COL];
            String mapRow;

            while ((mapRow = mapReader.readLine()) != null) {
                String[] mapRowData = mapRow.split(" ");

                if (Objects.equals(mapRowData[0], "BOARD")) {
                    int col = 0;
                    int row = Integer.parseInt(mapRowData[1]);
                    int width = Integer.parseInt(mapRowData[2]);
                    int layer = Integer.parseInt(mapRowData[3]);
                    String tileData = mapRowData[4];

                    for (int i = 0; i < width*2; i+=2) {
                        String tile = "" + tileData.charAt(i) + tileData.charAt(i+1);
                        mapTiles[layer][row][col] = tile;
                        col++;
                    }
                }
            }
        } catch (IOException | NullPointerException e) {
        e.printStackTrace();
        // TODO: Handle malformed map files.
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < GamePanel.WORLD_MAX_COL && worldRow < GamePanel.WORLD_MAX_ROW) {

            int worldX = worldCol * GamePanel.TILE_SIZE;
            int worldY = worldRow * GamePanel.TILE_SIZE;
            int screenX = camera.convertToScreenX(worldX);
            int screenY = camera.convertToScreenY(worldY);

            if (camera.isInCameraFrame(worldX, worldY)) {
                for (int layer = 0; layer < mapLayers; layer++) {
                    g2.drawImage(tileTypes[Integer.parseInt(mapTiles[layer][worldRow][worldCol])].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
                }
            }

            worldCol++;

            if (worldCol == GamePanel.WORLD_MAX_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
