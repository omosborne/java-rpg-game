package editor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TilesetManager {

    private int width;
    private int height;

    private EditorPanel editor;
    private BufferedImage tileset;
    private final List<Tile> tiles = new ArrayList<>();

    public TilesetManager(EditorPanel editor) {
        this.editor = editor;
        loadTileset("/game/images/overworld.png");
    }

    private void loadTileset(String tilesetFilePath) {
        try {
            tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tilesetFilePath)));

            width = tileset.getWidth();
            height = tileset.getHeight();

            editor.getTilesetViewer().setPreferredSize(new Dimension(width*6, height*6));

            int columns = width / 16;
            int rows = height / 16;

            String tileKeyList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int tileKeyListLength = tileKeyList.length();
            int firstKeyIndex = 0;
            int secondKeyIndex = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    BufferedImage tileImage = tileset.getSubimage(j * 16, i * 16, 16, 16);

                    Tile tile = new TilesetTile(editor.getTilesetViewer());
                    tile.setImage(tileImage);

                    if (secondKeyIndex >= tileKeyListLength) {
                        firstKeyIndex++;
                        secondKeyIndex = 0;
                    }

                    String tileKey = "" + tileKeyList.charAt(firstKeyIndex) + tileKeyList.charAt(secondKeyIndex);
                    tile.setCode(tileKey);
                    tiles.add(tile);
                    editor.getTilesetViewer().addTile(tile);
                    secondKeyIndex++;
                }
            }
        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(tileset, 0, 0, width*6, height*6, null);
    }
}
