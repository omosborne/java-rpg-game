package editor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class LevelManager {

    private EditorPanel editor;

    private final int levelWidthInTiles = 20;
    private final int levelHeightInTiles = 20;
    private final int levelTileSize = 96;

    private String tilesetFilePath = EditorPanel.DEFAULT_TILESET;
    private String[][][] mapTiles;
    private int mapLayers;
    private final HashMap<String, BufferedImage> tiles = new HashMap();

    public void setTileset(String newTilesetFilePath) {
        tilesetFilePath = newTilesetFilePath;
    }

    public LevelManager(EditorPanel editor) {
        this.editor = editor;
        loadLevelTileset();
        loadLevel("/game/maps/world05a.txt");
    }

    private void loadLevelTileset() {
        try {
            BufferedImage tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tilesetFilePath)));

            int columns = tileset.getWidth() / 16;
            int rows = tileset.getHeight() / 16;

            String tileKeyList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int tileKeyListLength = tileKeyList.length();
            int firstKeyIndex = 0;
            int secondKeyIndex = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    BufferedImage tileImage = tileset.getSubimage(j * 16, i * 16, 16, 16);

                    if (secondKeyIndex >= tileKeyListLength) {
                        firstKeyIndex++;
                        secondKeyIndex = 0;
                    }

                    String tileKey = "" + tileKeyList.charAt(firstKeyIndex) + tileKeyList.charAt(secondKeyIndex);
                    tiles.put(tileKey, tileImage);
                    secondKeyIndex++;
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void loadLevel(String levelFilePath) {
        try (InputStream mapFileInput = Objects.requireNonNull(getClass().getResourceAsStream(levelFilePath));
             BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapFileInput))) {
            mapLayers = Integer.parseInt(mapReader.readLine());
            mapTiles = new String[mapLayers][levelWidthInTiles][levelHeightInTiles];
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

    private void createEmptyLevel() {

    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < levelWidthInTiles && row < levelHeightInTiles) {

            int levelX = col * levelTileSize;
            int levelY = row * levelTileSize;

            if (editor.drawInactiveLayers()) {
                for (int layer = 0; layer < mapLayers; layer++) {
                    g2.drawImage(tiles.get(mapTiles[layer][row][col]), levelX, levelY, levelTileSize, levelTileSize, null);
                }
            }
            else {
                g2.drawImage(tiles.get(mapTiles[editor.getCurrentLayer()][row][col]), levelX, levelY, levelTileSize, levelTileSize, null);
            }

            col++;

            if (col == levelWidthInTiles) {
                col = 0;
                row++;
            }
        }
    }
}