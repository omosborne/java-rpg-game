package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemLayer extends JMenuItem implements ActionListener {

    private static int totalLayerMenuItems = 0;
    private static MenuItemLayer selectedLayer;
    private final int layer;

    public MenuItemLayer() {
        setText("Layer " + totalLayerMenuItems);
        layer = totalLayerMenuItems;

        if (totalLayerMenuItems == 0) setSelectedLayer(this);
        totalLayerMenuItems++;
        addActionListener(this);
    }

    private static void setSelectedLayer(MenuItemLayer newSelectedLayer) {
        if (totalLayerMenuItems != 0) {
            selectedLayer.setBackground(new Color(238, 238, 238));
            Main.getEditor().setSelectedLayer(newSelectedLayer.layer);
        }
        newSelectedLayer.setBackground(Color.lightGray);

        selectedLayer = newSelectedLayer;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        setSelectedLayer((MenuItemLayer) event.getSource());
    }
}
