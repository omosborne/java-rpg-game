package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EditorPanel extends JPanel {

    public static final int SCREEN_MIN_WIDTH = 1280;
    public static final int SCREEN_MIN_HEIGHT = 720;
    public static final int DEFAULT_TILE_SIZE = 96;
    public static final String DEFAULT_TILESET = "/game/images/overworld.png";

    private boolean showAllTileLayers = true;
    private int currentLayer = 0;

    private final LevelManager levelManager;
    private final TilesetManager tilesetManager;
    private final JPanel tilesetViewer;

    public int getCurrentLayer() {
        return currentLayer;
    }

    public boolean drawInactiveLayers() {
        return showAllTileLayers;
    }

    public void setDrawInactiveLayers(boolean drawInactiveLayers) {
        showAllTileLayers = drawInactiveLayers;
    }

    public JPanel getTilesetViewer() {
        return tilesetViewer;
    }

    public EditorPanel() {
        setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));

        JPanel levelViewer = new LevelViewer();
        levelViewer.setPreferredSize(new Dimension(1920, 1920));
        JScrollPane levelViewerScroll = new JScrollPane(levelViewer);
        levelViewer.setAutoscrolls(true);
        levelViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.66), SCREEN_MIN_HEIGHT));
        levelViewerScroll.getVerticalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.getHorizontalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        levelViewerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(levelViewerScroll);

        tilesetViewer = new TilesetViewer();
        JScrollPane tilesetViewerScroll = new JScrollPane(tilesetViewer);
        tilesetViewer.setAutoscrolls(true);
        tilesetViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.33), SCREEN_MIN_HEIGHT));
        tilesetViewerScroll.getVerticalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        tilesetViewerScroll.getHorizontalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        tilesetViewerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tilesetViewerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(tilesetViewerScroll);

        levelManager = new LevelManager(this);
        tilesetManager = new TilesetManager(this);

        levelViewer.repaint();
        tilesetViewer.repaint();
    }

    private class LevelViewer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            levelManager.draw(g2);
        }
    }

    private class TilesetViewer extends JPanel {

        private BufferedImage tileableImage;
        private TexturePaint transparentBackground;

        private TilesetViewer() {
            try {
                tileableImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/bg.png")));
                transparentBackground = new TexturePaint(tileableImage, new Rectangle(DEFAULT_TILE_SIZE / 2, DEFAULT_TILE_SIZE / 2));
            }
            catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setPaint(transparentBackground);
            g2.fillRect(0, 0, getWidth(), getHeight());

            tilesetManager.draw(g2);
        }

    }
}
