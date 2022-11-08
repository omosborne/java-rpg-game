package editor;

import javax.swing.*;
import java.awt.*;

public class LevelViewer extends JPanel {

    private EditorPanel editor;

    public LevelViewer(EditorPanel editor) {
        this.editor = editor;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        editor.getLevelManager().draw(graphics2D);
    }
}