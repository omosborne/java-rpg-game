package editor.menubar;

import editor.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemOpenLayer extends JMenuItem implements ActionListener {

    public MenuItemOpenLayer() {
        setText("Open");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser findFile = new JFileChooser();
        findFile.setDialogTitle("Open Level");
        findFile.setCurrentDirectory(new java.io.File("."));
        findFile.setFileFilter(new FileNameExtensionFilter("Level Files", "txt"));

        if (findFile.showOpenDialog(Main.getWindow()) == JFileChooser.APPROVE_OPTION) {
            Main.getEditor().openLevel(findFile.getSelectedFile().getPath());
        }
    }
}
