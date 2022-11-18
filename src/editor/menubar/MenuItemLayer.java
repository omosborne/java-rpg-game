package editor.menubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemLayer extends JMenuItem implements ActionListener {

    private MenuLayer layerMenu;
    private int layer;

    public int getLayer() {
        return layer;
    }

    public MenuItemLayer(MenuLayer layerMenu, int layer) {
        this.layerMenu = layerMenu;
        this.layer = layer;
        setText("Layer " + layer);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        MenuItemLayer layerItemClicked = (MenuItemLayer) event.getSource();
        layerMenu.setSelectedLayerItem(layerItemClicked.getLayer());
    }
}
