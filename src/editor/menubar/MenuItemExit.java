package editor.menubar;

import editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemExit extends JMenuItem implements ActionListener {

    private MenuItemSaveLevel saveItem;

    public MenuItemExit(MenuItemSaveLevel saveItem) {
        this.saveItem = saveItem;
        setText("Exit");
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
                System.exit(0);
            }
            else if (userChoice == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
        else {
            System.exit(0);
        }
    }
}
