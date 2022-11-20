package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MenuItemSaveLevel extends JMenuItem implements ActionListener {

    MenuItemSaveAsLevel saveAsItem;

    public MenuItemSaveLevel(MenuItemSaveAsLevel saveAsItem) {
        this.saveAsItem = saveAsItem;
        setText("Save");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String levelFilePath = Main.getEditor().getLevelFilePath();
        if (Objects.equals(levelFilePath, "")) {
            saveAsItem.actionPerformed(e);
        }
        else {
            Main.getEditor().saveLevel(levelFilePath);
        }
    }
}
