package editor.menubar;

import editor.EditorPanel;
import editor.Main;

import javax.swing.*;
import java.awt.*;

public class MenuLayer extends JMenu {

    private static final int NEW_LAYER_ITEM = 0;
    private static final int REMOVE_LAYER_ITEM = 1;

    private int totalVisibleLayerItems = 0;
    private MenuItemLayer selectedLayerItem;

    public int getSelectedLayer() {
        return selectedLayerItem.getLayer();
    }

    public int getTotalVisibleLayerItems() {
        return totalVisibleLayerItems;
    }

    private void updateTotalVisibleLayerItems(int delta) {
        if (totalVisibleLayerItems + delta < 0) totalVisibleLayerItems = 0;
        else if (totalVisibleLayerItems + delta > EditorPanel.MAX_LAYERS) totalVisibleLayerItems = EditorPanel.MAX_LAYERS;
        else totalVisibleLayerItems += delta;
    }

    public MenuLayer() {
        setText("Layers");
    }

    public void addNewLayerItem() {
        MenuItemLayer layerItem = new MenuItemLayer(this);
        add(layerItem);
    }

    public void setSelectedLayerItem(int layer) {
        MenuItemLayer newSelectedLayer = getLayerItemFromLayer(layer);
        if (totalVisibleLayerItems != 0) {
            selectedLayerItem.setBackground(new Color(238, 238, 238));
            Main.getEditor().setSelectedLayer(newSelectedLayer.getLayer());
        }
        newSelectedLayer.setBackground(Color.lightGray);
        selectedLayerItem = newSelectedLayer;
    }

    public void updateNewLayerItemVisibility(boolean isVisible) {
        getMenuComponent(NEW_LAYER_ITEM).setVisible(isVisible);
    }

    public void updateRemoveLayerItemVisibility(boolean isVisible) {
        getMenuComponent(REMOVE_LAYER_ITEM).setVisible(isVisible);
    }

    public void updateLayerItemVisibility(int layer, boolean isVisible) {
        getLayerItemFromLayer(layer).setVisible(isVisible);
        updateTotalVisibleLayerItems(isVisible ? 1 : -1);

        updateRemoveLayerItemVisibility(totalVisibleLayerItems != 1);
        updateNewLayerItemVisibility(totalVisibleLayerItems != EditorPanel.MAX_LAYERS);
    }

    private MenuItemLayer getLayerItemFromLayer(int layer) {
        return (MenuItemLayer) getMenuComponent(layer+2);
    }
}
