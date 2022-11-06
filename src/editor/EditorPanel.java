package editor;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    public static final int SCREEN_MIN_WIDTH = 1280;
    public static final int SCREEN_MIN_HEIGHT = 720;
    public static final String DEFAULT_TILESET = "/game/images/overworld.png";

    private final LevelManager levelManager;
    private final TilesetManager tilesetManager;

    public EditorPanel() {
        setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));

        JPanel levelViewer = new levelViewer();
        JScrollPane levelViewerScroll = new JScrollPane(levelViewer);
        levelViewer.setAutoscrolls(true);
        levelViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.66), SCREEN_MIN_HEIGHT));
        levelViewerScroll.setLocation(0, 0);
        add(levelViewerScroll);

        JPanel tilesetViewer = new tilesetViewer();
        JScrollPane tilesetViewerScroll = new JScrollPane(tilesetViewer);
        tilesetViewer.setAutoscrolls(true);
        tilesetViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.33), SCREEN_MIN_HEIGHT));
        tilesetViewerScroll.setLocation((int) (SCREEN_MIN_WIDTH * 0.66), 50);
        add(tilesetViewerScroll);

        levelManager = new LevelManager();
        tilesetManager = new TilesetManager();

        levelViewer.repaint();
        tilesetViewer.repaint();
    }

    class levelViewer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            levelManager.draw(g2);
        }

    }

    class tilesetViewer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            tilesetManager.draw(g2);
        }

    }
}
