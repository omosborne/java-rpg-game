package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TilesetViewer extends JPanel {

    private Tile selectedTile;
    private EditorPanel editor;
    private BufferedImage tileableImage;
    private TexturePaint transparentBackground;

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile tile) {
        selectedTile = tile;
    }

    public TilesetViewer(EditorPanel editor) {
        this.editor = editor;
        try {
            tileableImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/bg.png")));
            transparentBackground = new TexturePaint(tileableImage, new Rectangle(EditorPanel.DEFAULT_TILE_SIZE / 2, EditorPanel.DEFAULT_TILE_SIZE / 2));
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void addTile(Tile tile) {
        add(tile);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(transparentBackground);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        editor.getTilesetManager().draw(graphics2D);
    }

}
