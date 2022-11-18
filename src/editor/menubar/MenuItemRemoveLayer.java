package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemRemoveLayer extends JMenuItem implements ActionListener {

    private final MenuLayer layerMenu;

    public MenuItemRemoveLayer(MenuLayer layerMenu) {
        this.layerMenu = layerMenu;
        setText("Remove Layer");
        addActionListener(this);
    }

    private void removeLayer() {
        int layer = layerMenu.getTotalVisibleLayerItems()-1;
        layerMenu.updateLayerItemVisibility(layer, false);
        Main.getEditor().getLevelManager().removeSelectedLayer();
        allocateNewSelectedLayer();
    }

    private void allocateNewSelectedLayer() {
        int layer = layerMenu.getSelectedLayer();
        if (layer == 0 || layer == 1) layerMenu.setSelectedLayerItem(0);
        else layerMenu.setSelectedLayerItem(layer-1);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        removeLayer();
    }
}
