package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LevelViewer extends JPanel {

    private EditorPanel editor;
    private BufferedImage tileableImage;
    private TexturePaint transparentBackground;

    public LevelViewer(EditorPanel editor) {
        this.editor = editor;
        try {
            tileableImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/bg.png")));
            transparentBackground = new TexturePaint(tileableImage, new Rectangle(EditorPanel.DEFAULT_TILE_SIZE / 2, EditorPanel.DEFAULT_TILE_SIZE / 2));
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void clearTileComponents() {
        for (Component component : getComponents()) remove(component);
    }

    public void addTile(Tile tile) {
        add(tile);
    }

    public void updateTile(LevelTile tileToUpdate) {
        Tile updatedTile = editor.getTilesetViewer().getSelectedTile();
        if (updatedTile == null) return;

        editor.getLevelManager().changeMapTile(tileToUpdate, updatedTile);
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(transparentBackground);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        editor.getLevelManager().draw(graphics2D);
    }
}