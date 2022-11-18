package editor.menubar;

import editor.EditorPanel;
import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemNewLayer extends JMenuItem implements ActionListener {

    private final MenuLayer layerMenu;

    public MenuItemNewLayer (MenuLayer layerMenu) {
        this.layerMenu = layerMenu;
        setText("New Layer");
        addActionListener(this);
    }

    private void addNewLayer() {
        int layer = layerMenu.getTotalVisibleLayerItems();
        layerMenu.updateLayerItemVisibility(layer, true);
        Main.getEditor().getLevelManager().addNewLayer();
        if (layerMenu.getTotalVisibleLayerItems() == EditorPanel.MAX_LAYERS) layerMenu.updateNewLayerItemVisibility(false);
        layerMenu.setSelectedLayerItem(layer);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        addNewLayer();
    }
}
