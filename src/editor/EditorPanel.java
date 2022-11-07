package editor;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    public static final int SCREEN_MIN_WIDTH = 1280;
    public static final int SCREEN_MIN_HEIGHT = 720;
    public static final int DEFAULT_TILE_SIZE = 96;
    public static final String DEFAULT_TILESET = "/game/images/overworld.png";

    private final LevelManager levelManager;
    private final TilesetManager tilesetManager;
    private final JPanel tilesetViewer;

    public JPanel getTilesetViewer() {
        return tilesetViewer;
    }

    public EditorPanel() {
        setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));

        JPanel levelViewer = new levelViewer();
        levelViewer.setPreferredSize(new Dimension(1920, 1920));
        JScrollPane levelViewerScroll = new JScrollPane(levelViewer);
        levelViewer.setAutoscrolls(true);
        levelViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.66), SCREEN_MIN_HEIGHT));
        levelViewerScroll.getVerticalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.getHorizontalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        levelViewerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(levelViewerScroll);

        tilesetViewer = new tilesetViewer();
        JScrollPane tilesetViewerScroll = new JScrollPane(tilesetViewer);
        tilesetViewer.setAutoscrolls(true);
        tilesetViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.33), SCREEN_MIN_HEIGHT));
        tilesetViewerScroll.getVerticalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        tilesetViewerScroll.getHorizontalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        tilesetViewerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tilesetViewerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(tilesetViewerScroll);

        levelManager = new LevelManager();
        tilesetManager = new TilesetManager(this);

        levelViewer.repaint();
        tilesetViewer.repaint();
    }

    private class levelViewer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            levelManager.draw(g2);
        }
    }

    private class tilesetViewer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            tilesetManager.draw(g2);
        }

    }
}
