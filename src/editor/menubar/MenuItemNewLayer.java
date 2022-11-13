package editor.menubar;

import editor.EditorPanel;
import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemNewLayer extends JMenuItem implements ActionListener {

    private final JMenu layerMenu;

    public MenuItemNewLayer (JMenu layerMenu) {
        this.layerMenu = layerMenu;
        setText("New Layer");
        addActionListener(this);
    }

    private void addNewLayer() {
        if (Main.getEditor().getTotalLayers() < EditorPanel.MAX_LAYERS) {
            System.out.println(Main.getEditor().getTotalLayers());
            JMenuItem layer = new MenuItemLayer();
            layerMenu.add(layer);
            Main.getEditor().getLevelManager().addNewLayer();
            if (Main.getEditor().getTotalLayers() == EditorPanel.MAX_LAYERS) layerMenu.remove(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        addNewLayer();
    }
}
