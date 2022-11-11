package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemToggleGrid extends JMenuItem implements ActionListener {

    private boolean isGridVisible = false;

    public MenuItemToggleGrid() {
        setText("Show Grid");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (isGridVisible) {
            Main.getEditor().setGridVisibility(false);
            isGridVisible = false;
            setText("Show Grid");
        }
        else {
            Main.getEditor().setGridVisibility(true);
            isGridVisible = true;
            setText("Hide Grid");
        }
    }
}
