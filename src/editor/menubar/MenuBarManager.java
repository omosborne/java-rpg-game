package editor.menubar;

import editor.EditorPanel;
import editor.Main;

import javax.swing.*;

public class MenuBarManager {

    private JMenuBar menu;

    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenu menuView;
    private MenuLayer menuLayer;

    public MenuBarManager() {
        menu = new JMenuBar();
        Main.getWindow().setJMenuBar(menu);

        menu.add(getFileMenu());
        menu.add(getEditMenu());
        menu.add(getViewMenu());
        menu.add(getLayerMenu());
    }

    private JMenu getFileMenu() {
        menuFile = new JMenu("File");

        JMenuItem newLevel = new JMenuItem("New");
        JMenuItem openLevel = new JMenuItem("Open");
        JMenuItem saveAsLevel = new JMenuItem("Save As");
        JMenuItem saveLevel = new JMenuItem("Save");
        JMenuItem exitEditor = new JMenuItem("Exit");

        menuFile.add(newLevel);
        menuFile.add(openLevel);
        menuFile.add(saveAsLevel);
        menuFile.add(saveLevel);
        menuFile.add(exitEditor);

        return menuFile;
    }

    private JMenu getEditMenu() {
        menuEdit = new JMenu("Edit");

        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        menuEdit.add(undo);
        menuEdit.add(redo);

        return menuEdit;
    }

    private JMenu getViewMenu() {
        menuView = new JMenu("View");

        JMenuItem toggleLayers = new MenuItemToggleLayers();
        JMenuItem toggleGrid = new MenuItemToggleGrid();

        menuView.add(toggleLayers);
        menuView.add(toggleGrid);

        return menuView;
    }

    private JMenu getLayerMenu() {
        menuLayer = new MenuLayer();

        MenuItemNewLayer createNewLayer = new MenuItemNewLayer(menuLayer);
        menuLayer.add(createNewLayer);
        MenuItemRemoveLayer removeLayer = new MenuItemRemoveLayer(menuLayer);
        menuLayer.add(removeLayer);
        for (int layer = 0; layer < 3; layer++) {
            menuLayer.addNewLayerItem();
        }

        return menuLayer;
    }

    public void allocateLayerMenuItems(int totalLayers) {
        for (int layer = totalLayers; layer < EditorPanel.MAX_LAYERS; layer++) {
            menuLayer.updateLayerItemVisibility(layer, false);
        }
    }
}
