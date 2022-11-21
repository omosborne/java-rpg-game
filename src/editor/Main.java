package editor;

import editor.menubar.MenuBarManager;

import javax.swing.*;

public class Main {

    private static JFrame window;
    private static EditorPanel editor;
    private static MenuBarManager menuBarManager;

    public static JFrame getWindow() {
        return window;
    }

    public static EditorPanel getEditor() {
        return editor;
    }

    public static MenuBarManager getMenuBarManager() {
        return menuBarManager;
    }

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBarManager = new MenuBarManager();

        editor = new EditorPanel();
        window.add(editor);

        window.pack();

        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
