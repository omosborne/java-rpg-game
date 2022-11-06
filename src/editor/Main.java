package editor;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newLevel = new JMenuItem("New");
        JMenuItem openLevel = new JMenuItem("Open");
        JMenuItem saveAsLevel = new JMenuItem("Save As");
        JMenuItem saveLevel = new JMenuItem("Save");
        JMenuItem exitEditor = new JMenuItem("Exit");

        file.add(newLevel);
        file.add(openLevel);
        file.add(saveAsLevel);
        file.add(saveLevel);
        file.add(exitEditor);

        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        edit.add(undo);
        edit.add(redo);

        JMenu view = new JMenu("View");
        menuBar.add(view);

        JMenuItem hideLayers = new JMenuItem("Hide Inactive Layers");
        JMenuItem toggleGrid = new JMenuItem("Grid");

        view.add(hideLayers);
        view.add(toggleGrid);

        JMenu layers = new JMenu("Layers");
        menuBar.add(layers);

        JMenuItem newLayer = new JMenuItem("New Layer");
        JMenuItem layer0 = new JMenuItem("Layer 0");

        layers.add(newLayer);
        layers.add(layer0);

        EditorPanel editorPanel = new EditorPanel();
        window.add(editorPanel);


        window.pack();

        window.setLayout(null);
        window.setResizable(false);
        window.setTitle("Level Editor");
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
