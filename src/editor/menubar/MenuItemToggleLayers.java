package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemToggleLayers extends JMenuItem implements ActionListener {

    private boolean areAllLayersVisible = true;

    public MenuItemToggleLayers() {
        setText("Hide Inactive Layers");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (areAllLayersVisible) {
            Main.getEditor().setDrawInactiveLayers(false);
            areAllLayersVisible = false;
            setText("Show All Layers");
        }
        else {
            Main.getEditor().setDrawInactiveLayers(true);
            areAllLayersVisible = true;
            setText("Hide Inactive Layers");
        }
    }
}
