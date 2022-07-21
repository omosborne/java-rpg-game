package game;

import javax.swing.JPanel;
import java.awt.*;
import game.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    // Main screen settings
    final byte originalTileSize = 16;

    public final int tileSize = originalTileSize * 3;

    final int screenMaxCol = 16;
    final int screenMaxRow = 12;

    final int screenWidth = screenMaxCol * tileSize;
    final int screenHeight = screenMaxRow * tileSize;

    KeyInputHandler keyH = new KeyInputHandler();
    Thread gameThread;

    final byte MaxFPS = 60;

    Player player = new Player(this, keyH);

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run () {
        // Game Loop Variables
        double drawInterval = 1000000000/MaxFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update () {
        player.update();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}