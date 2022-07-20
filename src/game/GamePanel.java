package game;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    // Main screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;

    final int screenMaxCol = 16;
    final int screenMaxRow = 12;

    final int screenWidth = screenMaxCol * tileSize;
    final int screenHeight = screenMaxRow * tileSize;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
    }
}
