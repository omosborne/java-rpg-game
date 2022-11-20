package editor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LevelManager {

    private static final int LEVEL_WIDTH_IN_TILES = 20;
    private static final int LEVEL_HEIGHT_IN_TILES = 20;
    private static final int LEVEL_TILE_SIZE = 96;

    private final EditorPanel editor;

    private String tilesetFilePath = EditorPanel.DEFAULT_TILESET;
    private ArrayList<String[][]> levelTiles = new ArrayList<>();
    private final HashMap<String, BufferedImage> tiles = new HashMap();

    public int getTotalLayers() {
        return levelTiles.size();
    }

    public void setTileset(String newTilesetFilePath) {
        tilesetFilePath = newTilesetFilePath;
    }

    public LevelManager(EditorPanel editor) {
        this.editor = editor;
        loadLevelTileset();
        createEmptyLevel();
        loadTileComponentsInLevelLayer(editor.getSelectedLayer());
    }

    public void updateLevelTile(LevelTile levelTile, Tile newTile) {
        levelTiles.get(levelTile.getLayer())[levelTile.getLevelY()][levelTile.getLevelX()] = newTile.getCode();
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

    public void saveLevel(String levelFilePath) {
        try (FileOutputStream levelFileOut = new FileOutputStream(levelFilePath);
             ObjectOutputStream levelDataOut = new ObjectOutputStream(levelFileOut)) {
            levelDataOut.writeObject(levelTiles);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public void loadLevel(String levelFilePath) {
        try (FileInputStream levelFileIn = new FileInputStream(levelFilePath);
             ObjectInputStream levelDataIn = new ObjectInputStream(levelFileIn)) {
            levelTiles.clear();
            levelTiles = (ArrayList<String[][]>) levelDataIn.readObject();
            editor.setLevelFilePath(levelFilePath);
            Main.getMenuBarManager().allocateLayerMenuItems(levelTiles.size());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // TODO: Handle malformed level files.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadTileComponentsInLevelLayer(int layer) {

        LevelViewer levelViewer = editor.getLevelViewer();

        levelViewer.clearTileComponents();

        for (int row = 0; row < LEVEL_HEIGHT_IN_TILES; row++) {
            for (int col = 0; col < LEVEL_WIDTH_IN_TILES; col++) {
                Tile tile = new LevelTile(levelViewer, col, row, layer);
                String tileCode = levelTiles.get(layer)[row][col];
                tile.setCode(tileCode);
                tile.setImage(tiles.get(tileCode));
                levelViewer.addTile(tile);
            }
        }
    }

    public void addNewLayer() {
        levelTiles.add(new String[LEVEL_WIDTH_IN_TILES][LEVEL_HEIGHT_IN_TILES]);
        int newLayer = levelTiles.size() - 1;
        for (int row = 0; row < LEVEL_HEIGHT_IN_TILES; row++) {
            for (int col = 0; col < LEVEL_WIDTH_IN_TILES; col++) {
                levelTiles.get(newLayer)[row][col] = "AA";
            }
        }
    }

    public void removeSelectedLayer() {
        levelTiles.remove(editor.getSelectedLayer());
        editor.getLevelViewer().repaint();
    }

    private void createEmptyLevel() {
        levelTiles.clear();
        levelTiles.add(new String[LEVEL_WIDTH_IN_TILES][LEVEL_HEIGHT_IN_TILES]);

        for (int row = 0; row < LEVEL_HEIGHT_IN_TILES; row++) {
            for (int col = 0; col < LEVEL_WIDTH_IN_TILES; col++) {
                levelTiles.get(0)[row][col] = "AB";
            }
        }

        Main.getMenuBarManager().allocateLayerMenuItems(1);
    }

    public void draw(Graphics2D graphics2D) {
        int col = 0;
        int row = 0;
        int totalLayers = levelTiles.size();

        while (col < LEVEL_WIDTH_IN_TILES && row < LEVEL_HEIGHT_IN_TILES) {

            int levelX = col * LEVEL_TILE_SIZE;
            int levelY = row * LEVEL_TILE_SIZE;

            if (editor.drawInactiveLayers()) {
                for (int layer = 0; layer < totalLayers; layer++) {
                    graphics2D.drawImage(tiles.get(levelTiles.get(layer)[row][col]), levelX, levelY, LEVEL_TILE_SIZE, LEVEL_TILE_SIZE, null);
                }
            }
            else {
                graphics2D.drawImage(tiles.get(levelTiles.get(editor.getSelectedLayer())[row][col]), levelX, levelY, LEVEL_TILE_SIZE, LEVEL_TILE_SIZE, null);
            }

            col++;

            if (col == LEVEL_WIDTH_IN_TILES) {
                col = 0;
                row++;
            }
        }
    }
}
