package editor.menubar;

import editor.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemSaveAsLevel extends JMenuItem implements ActionListener {

    public MenuItemSaveAsLevel() {
        setText("Save As");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser saveFile = new JFileChooser();
        saveFile.setDialogTitle("Save Level");
        saveFile.setCurrentDirectory(new java.io.File("."));
        saveFile.setFileFilter(new FileNameExtensionFilter("Level Files", "level"));

        if (saveFile.showSaveDialog(Main.getWindow()) == JFileChooser.APPROVE_OPTION) {
            String levelFilePath = saveFile.getSelectedFile().getPath();
            Main.getEditor().saveLevel(levelFilePath.endsWith(".level") ? levelFilePath : levelFilePath + ".level");
        }
    }
}
