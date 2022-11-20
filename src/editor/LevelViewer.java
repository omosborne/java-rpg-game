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
    private Stroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

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

        editor.getLevelManager().updateLevelTile(tileToUpdate, updatedTile);
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(transparentBackground);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        editor.getLevelManager().draw(graphics2D);

        if (editor.isGridVisible()) drawGrid(graphics2D, editor.getGridColor());

        graphics2D.dispose();
    }

    private void drawGrid(Graphics2D graphics2D, Color gridColor) {
        int tileSize = EditorPanel.DEFAULT_TILE_SIZE;
        int levelWidthInTiles = getWidth() / tileSize;
        int levelHeightInTiles = getHeight() / tileSize;

        graphics2D.setColor(gridColor);
        graphics2D.setStroke(dashedStroke);

        for (int col = 0, row = 0; col < 20 && row < 20; col++, row++) {
            graphics2D.drawLine(col * tileSize, 0, col * tileSize, levelWidthInTiles * tileSize);
            graphics2D.drawLine(0, row * tileSize, levelHeightInTiles * tileSize, row * tileSize);
        }
    }
}