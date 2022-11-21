package editor;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    public static final int SCREEN_MIN_WIDTH = 1280;
    public static final int SCREEN_MIN_HEIGHT = 720;
    public static final int MAX_LAYERS = 3;
    public static final int DEFAULT_TILE_SIZE = 96;
    public static final String DEFAULT_TILESET = "/game/images/overworld.png";

    private String levelFilePath = "";
    private String levelTitle = "untitled";

    private boolean hasSaved = false;

    private boolean showGrid = false;
    private Color gridColor = Color.black;

    private boolean showAllTileLayers = true;
    private int selectedLayer = 0;

    private final LevelManager levelManager;
    private final LevelViewer levelViewer;
    private final TilesetManager tilesetManager;
    private final TilesetViewer tilesetViewer;

    public String getLevelFilePath() {
        return levelFilePath;
    }

    public void setLevelFilePath(String filePath) {
        levelFilePath = filePath;
        String[] filePathItems = levelFilePath.split("\\\\");
        setLevelTitle(filePathItems[filePathItems.length - 1]);
        Main.getWindow().setTitle("Level Editor - " + getLevelTitle());
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public void createNewLevel() {
        setLevelTitle("untitled");
        levelManager.createEmptyLevel();
        setSelectedLayer(0);
        levelFilePath = "";
        levelViewer.repaint();
    }

    public void setHasSaved(boolean hasSaved) {
        this.hasSaved = hasSaved;
        if (hasSaved) Main.getWindow().setTitle("Level Editor - " + getLevelTitle());
        else Main.getWindow().setTitle("Level Editor - " + getLevelTitle() + "*");
    }

    public boolean hasUnsavedChanges() {
        return !hasSaved;
    }

    public void setGridVisibility(boolean gridVisibility) {
        showGrid = gridVisibility;
        levelViewer.repaint();
    }

    public boolean isGridVisible() {
        return showGrid;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public int getSelectedLayer() {
        return selectedLayer;
    }

    public void setSelectedLayer(int layer) {
        selectedLayer = layer;
        levelManager.loadTileComponentsInLevelLayer(layer);
        levelViewer.repaint();
        levelViewer.scrollRectToVisible(new Rectangle(1,1)); // temp bug fix for updating components
    }

    public boolean drawInactiveLayers() {
        return showAllTileLayers;
    }

    public void setDrawInactiveLayers(boolean drawInactiveLayers) {
        showAllTileLayers = drawInactiveLayers;
        levelViewer.repaint();
    }

    public int getTotalLayers() {
        return levelManager.getTotalLayers();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public LevelViewer getLevelViewer() {
        return levelViewer;
    }

    public TilesetManager getTilesetManager() {
        return tilesetManager;
    }

    public TilesetViewer getTilesetViewer() {
        return tilesetViewer;
    }

    public void openLevel(String levelFilePath) {
        levelManager.loadLevel(levelFilePath);
        setSelectedLayer(0);
    }

    public void saveLevel(String levelFilePath) {
        levelManager.saveLevel(levelFilePath);
    }

    public EditorPanel() {
        setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));

        levelViewer = new LevelViewer(this);
        levelViewer.setPreferredSize(new Dimension(1920, 1920));
        levelViewer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JScrollPane levelViewerScroll = new JScrollPane(levelViewer);
        levelViewerScroll.setPreferredSize(new Dimension((int) (SCREEN_MIN_WIDTH * 0.66), SCREEN_MIN_HEIGHT));
        levelViewerScroll.getVerticalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.getHorizontalScrollBar().setUnitIncrement(DEFAULT_TILE_SIZE);
        levelViewerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        levelViewerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(levelViewerScroll);

        tilesetViewer = new TilesetViewer(this);
        tilesetViewer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JScrollPane tilesetViewerScroll = new JScrollPane(tilesetViewer);
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
}
