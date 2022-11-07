package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TilesetManager {

    private EditorPanel editor;
    private BufferedImage tileset;

    public TilesetManager(EditorPanel editor) {
        this.editor = editor;
        loadTileset("/game/images/overworld.png");
    }

    private void loadTileset(String tilesetFilePath) {
        try {
            tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tilesetFilePath)));
            editor.getTilesetViewer().setPreferredSize(new Dimension(tileset.getWidth()*6, tileset.getHeight()*6));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(tileset, 0, 0, tileset.getWidth()*6, tileset.getHeight()*6, null);
    }
}
