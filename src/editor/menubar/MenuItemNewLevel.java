package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemNewLevel extends JMenuItem implements ActionListener {

    MenuItemSaveLevel saveItem;

    public MenuItemNewLevel(MenuItemSaveLevel saveItem) {
        this.saveItem = saveItem;
        setText("New");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Main.getEditor().hasUnsavedChanges()) {
            int userChoice = JOptionPane.showConfirmDialog(
                    Main.getWindow(),
                    "You have unsaved changes! Do you want to save this level?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (userChoice == JOptionPane.YES_OPTION) {
                saveItem.actionPerformed(e);
                Main.getEditor().createNewLevel();
            }
            else if (userChoice == JOptionPane.NO_OPTION) {
                Main.getEditor().createNewLevel();
            }
        }
        else {
            Main.getEditor().createNewLevel();
        }
    }
}
