package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class TileManager {
    private final GamePanel gp;
    private final Camera camera;

    private String tilesetFileName;
    private String[][][] mapTiles;
    private int mapLayers;
    private final HashMap<String, Tile> tiles = new HashMap();

    private String map = GamePanel.DEFAULT_MAP;

    public String getMap() {
        return map;
    }

    public void setMap(String newMap) {
        map = newMap;
        loadMap();
    }

    public Tile getTileType(String mapTile) {
        return tiles.get(mapTile);
    }

    public String getMapTile(int layer, int row, int col) {
        return mapTiles[layer][row][col];
    }

    public TileManager(GamePanel gp) {
        this.gp = gp;
        camera = gp.getCamera();

        tilesetFileName = GamePanel.DEFAULT_TILESET;

        loadTileset();
        loadMap();
    }

    private void loadTileset() {
        try {
            BufferedImage tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/" + tilesetFileName + ".png")));

            int columns = tileset.getWidth() / 16;
            int rows = tileset.getHeight() / 16;

            String tileKeyList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int tileKeyListLength = tileKeyList.length();
            int firstKeyIndex = 0;
            char secondKeyIndex = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    BufferedImage tileImage = tileset.getSubimage(j * 16, i * 16, 16, 16);
                    Tile tile = new Tile();
                    tile.setImage(tileImage);

                    if (secondKeyIndex >= tileKeyListLength) {
                        firstKeyIndex++;
                        secondKeyIndex = 0;
                    }

                    String tileKey = "" + tileKeyList.charAt(firstKeyIndex) + tileKeyList.charAt(secondKeyIndex);
                    tiles.put(tileKey, tile);
                    secondKeyIndex++;
                }
            }
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
                    g2.drawImage(tiles.get(mapTiles[layer][worldRow][worldCol]).getImage() , screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
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
